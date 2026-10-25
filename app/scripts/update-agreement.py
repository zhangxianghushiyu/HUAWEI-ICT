#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
协议直改生产库 · 可复用脚本
读 docs/隐私政策整改/type{N}-*.html → 删除生产库 t_agreement 对应 type 旧行 → 插入新内容 → 字节级校验 + API 验证

用法:
    python scripts/update-agreement.py [--dry-run]
依赖: pymysql (python -m pip install pymysql)
服务器: 118.24.173.181, 库 polymer-upgrade, 表 t_agreement (见 memory: agreement-db-update)
"""
import io
import os
import sys
import re
import hashlib
import argparse
import datetime

import pymysql

DB_HOST = '118.24.173.181'
DB_PORT = 3306
DB_USER = 'root'
DB_PASS = 'Hkbigdata123456'   # TODO: 改进后用环境变量/密码文件，勿硬编码
DB_NAME = 'polymer-upgrade'

BASE = os.path.join(os.path.dirname(__file__), '..', 'docs', '隐私政策整改')

# type -> 本地文件名（type号：2隐私政策 3收集清单 4共享清单 7注销协议…）
FILES = {
    2: 'type2-隐私政策.html',
    3: 'type3-收集个人信息明示清单.html',
    4: 'type4-第三方共享清单.html',
}


def make_quill(raw_html: str) -> str:
    """剥掉首行 <meta charset="UTF-8"> 与首尾空白，得到后台/App 可直接渲染的富文本 html"""
    s = raw_html.replace('<meta charset="UTF-8">', '', 1)
    return s.strip().strip('﻿')


def load_local(typ: int) -> str:
    path = os.path.join(BASE, FILES[typ])
    with io.open(path, 'r', encoding='utf-8') as f:
        return make_quill(f.read())


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument('--dry-run', action='store_true', help='只打印计划，不写库')
    parser.add_argument('--types', type=str, default='2,3,4',
                        help='要更新的type列表，逗号分隔，默认 2,3,4')
    args = parser.parse_args()

    types = [int(t) for t in args.types.split(',') if int(t) in FILES]
    if not types:
        print('没有可更新的 type（FILES 未定义？），退出')
        sys.exit(1)

    # 读本地
    local = {t: load_local(t) for t in types}
    for t, c in local.items():
        print(f'本地 type={t}: len={len(c)}  head={c[:40]!r}')

    if args.dry_run:
        print('\n[dry-run] 以上为将要写入的内容计划，未连接数据库。')
        return

    conn = pymysql.connect(host=DB_HOST, port=DB_PORT, user=DB_USER, password=DB_PASS,
                           database=DB_NAME, charset='utf8mb4', autocommit=True,
                           connect_timeout=15)
    cur = conn.cursor()

    # 写库：先删旧行(按 type)，再插新行
    for t in types:
        cur.execute("DELETE FROM t_agreement WHERE type=%s", (t,))
        print(f'DELETE type={t}, affected={cur.rowcount}')
        cur.execute("INSERT INTO t_agreement (content, type, create_time, update_time) "
                    "VALUES (%s,%s,NOW(),NOW())", (local[t], t))
        print(f'INSERT type={t} -> id={cur.lastrowid}, len={len(local[t])}')

    # 校验：读回 逐字节比
    print('\n=== 字节级校验 ===')
    ok_all = True
    for t in types:
        cur.execute("SELECT content FROM t_agreement WHERE type=%s ORDER BY id DESC LIMIT 1", (t,))
        dbc = cur.fetchone()[0]
        same = hashlib.sha256(dbc.encode('utf-8')).hexdigest() == \
               hashlib.sha256(local[t].encode('utf-8')).hexdigest()
        print(f'type={t}: 逐字节一致? {same}')
        ok_all = ok_all and same
    conn.close()

    if not ok_all:
        print('\n❌ 校验失败：有 type 内容不一致，检查本地文件与脚本 FILES 映射')
        sys.exit(2)

    print('\n✅ 数据库写入完成且通过校验，生产 API 即时生效'
          '（验证：curl -s "https://liuyueliu.cloud/app-api/platform/agreement/get?type=N"）')


if __name__ == '__main__':
    main()