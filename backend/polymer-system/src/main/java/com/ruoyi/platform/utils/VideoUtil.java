package com.ruoyi.platform.utils;

import lombok.extern.slf4j.Slf4j;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;
import ws.schild.jave.info.VideoInfo;
import ws.schild.jave.info.VideoSize;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 视频工具：解析网络视频的真实宽高
 */
@Slf4j
public class VideoUtil {

    /**
     * 解析网络视频宽高
     *
     * @param url 视频地址
     * @return int[]{width, height}；下载失败/无视频流/解析失败时返回 null
     */
    public static int[] getVideoSize(String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }
        try {
            MultimediaInfo info = new MultimediaObject(new URL(url)).getInfo();
            VideoInfo video = info.getVideo();
            if (video == null) {
                return null;
            }
            VideoSize size = video.getSize();
            if (size == null) {
                return null;
            }
            return new int[]{size.getWidth(), size.getHeight()};
        } catch (Exception e) {
            log.warn("解析视频宽高失败: {}", url, e);
            return null;
        }
    }

    /** 目标输出：9:16 竖屏 */
    public static final int TARGET_WIDTH = 720;
    public static final int TARGET_HEIGHT = 1280;

    /**
     * 将网络视频下载并模糊填充转码成 9:16 竖屏（720x1280）。
     * 原视频完整居中，四周用模糊背景填满，不裁切内容。
     *
     * @param url 视频地址
     * @return 转码后的临时文件（调用方负责删除）；下载/转码失败返回 null
     */
    public static File transcodeTo916(String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }
        File input = null;
        File output = null;
        try {
            // 1. 下载到临时文件
            input = File.createTempFile("video-src-", suffixOf(url));
            try (InputStream in = new URL(url).openStream()) {
                Files.copy(in, input.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }

            // 2. ffmpeg 模糊填充转 9:16
            output = File.createTempFile("video-916-", ".mp4");
            String ffmpeg = System.getProperty("video.ffmpeg.path", "ffmpeg");
            String filter = "[0:v]split=2[bg][fg];"
                    + "[bg]scale=" + TARGET_WIDTH + ":" + TARGET_HEIGHT + ":force_original_aspect_ratio=increase,"
                    + "crop=" + TARGET_WIDTH + ":" + TARGET_HEIGHT + ",boxblur=20:5[bg];"
                    + "[fg]scale=" + TARGET_WIDTH + ":" + TARGET_HEIGHT + ":force_original_aspect_ratio=decrease[fg];"
                    + "[bg][fg]overlay=(W-w)/2:(H-h)/2[v]";
            List<String> cmd = new ArrayList<>();
            cmd.add(ffmpeg);
            cmd.add("-y");
            cmd.add("-i");
            cmd.add(input.getAbsolutePath());
            cmd.add("-filter_complex");
            cmd.add(filter);
            cmd.add("-map");
            cmd.add("[v]");
            cmd.add("-map");
            cmd.add("0:a?");
            cmd.add("-c:v");
            cmd.add("libx264");
            cmd.add("-preset");
            cmd.add("veryfast");
            cmd.add("-crf");
            cmd.add("23");
            cmd.add("-c:a");
            cmd.add("aac");
            cmd.add("-b:a");
            cmd.add("128k");
            cmd.add("-movflags");
            cmd.add("+faststart");
            cmd.add(output.getAbsolutePath());

            Process process = new ProcessBuilder(cmd).redirectErrorStream(true).start();
            StringBuilder ffLog = new StringBuilder();
            Thread reader = new Thread(() -> {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        if (ffLog.length() < 4096) {
                            ffLog.append(line).append('\n');
                        }
                    }
                } catch (Exception ignored) {
                }
            });
            reader.setDaemon(true);
            reader.start();

            if (!process.waitFor(10, TimeUnit.MINUTES)) {
                process.destroyForcibly();
                log.warn("视频转码超时: {}", url);
                output.delete();
                return null;
            }
            if (process.exitValue() != 0) {
                log.warn("ffmpeg 转码失败 exit={}, url={}, output={}", process.exitValue(), url, ffLog);
                output.delete();
                return null;
            }
            return output;
        } catch (Exception e) {
            log.warn("视频转码异常: {}", url, e);
            if (output != null) {
                output.delete();
            }
            return null;
        } finally {
            if (input != null) {
                input.delete();
            }
        }
    }

    private static String suffixOf(String url) {
        int q = url.indexOf('?');
        String path = q > 0 ? url.substring(0, q) : url;
        int dot = path.lastIndexOf('.');
        int slash = path.lastIndexOf('/');
        return dot > slash ? path.substring(dot) : ".mp4";
    }
}
