import COS from 'cos-js-sdk-v5';
import { Message } from 'element-ui';
import { getSignature } from '@/api/system/oss';

/**
 * 分片上传至腾讯云COS
 * @param {File} file 文件对象
 * @param {Function} successCallback 成功回调
 * @param {Function} errorCallback 失败回调
 * @param {Function} progressCallback 进度回调
 */
export const multipartUploadToCos = async (
    file,
    successCallback,
    errorCallback,
    progressCallback
) => {
    if (file.size / 1024 / 1024 > 1024) {
        Message.error('上传文件不能大于1GB');
        errorCallback('上传文件不能大于1GB');
        return;
    }
    if (!file) {
        Message.error('上传文件不能为空');
        errorCallback('上传文件不能为空');
        return;
    }
    if (!file.name) {
        Message.error('上传文件名称不能为空');
        errorCallback('上传文件名称不能为空');
        return;
    }

    try {
        // 获取STS临时凭证
        const res = await getSignature();

        if (res.code !== 200) {
            errorCallback(res.msg);
            return;
        }

        // 注意：后端返回 accessKeyId / accessKeySecret / securityToken / bucket / endpoint / region / today / fileName
        // endpoint = "cos.{region}.myqcloud.com"（域名后缀）
        // region   = "ap-guangzhou" 等（地域代码）
        const {
            accessKeyId,
            accessKeySecret,
            securityToken,
            bucket,
            endpoint,
            region,
            today,
            fileName,
        } = res.data;

        // 文件后缀
        const extensionName = `.${file.name.substring(file.name.lastIndexOf('.') + 1)}`;
        // COS对象Key
        const key = `${today}/${fileName}${extensionName}`;

        // 创建COS实例：用直接凭证方式，最简洁可靠
        const cos = new COS({
            SecretId: accessKeyId,
            SecretKey: accessKeySecret,
            SecurityToken: securityToken,
        });

        // 上传文件（自动处理分片）
        cos.uploadFile(
            {
                Bucket: bucket,
                Region: region, // 这里传真正的地域代码，如 "ap-guangzhou"
                Key: key,
                Body: file,
                SliceSize: 1024 * 1024 * 5, // 5MB以上自动分片
                onProgress: function (progressData) {
                    progressCallback(progressData.percent);
                },
            },
            function (err, data) {
                if (err) {
                    console.error('COS上传失败:', err);
                    const msg = err.error ? `${err.error.Code}: ${err.error.Message}` : (err.message || '上传失败');
                    errorCallback(msg);
                } else {
                    // 拼接完整URL
                    const url = `https://${bucket}.${endpoint}/${key}`;
                    successCallback({ url, Location: url, ...data });
                }
            }
        );
    } catch (err) {
        console.error('获取STS凭证失败:', err);
        errorCallback(err);
    }
};
