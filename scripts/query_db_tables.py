#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""只读：列出 polymer-upgrade 库相关表结构（临时查询脚本）"""
import pymysql

DB_HOST = '118.24.173.181'
DB_PORT = 3306
DB_USER = 'root'
DB_PASS = 'Hkbigdata123456'
DB_NAME = 'polymer-upgrade'

conn = pymysql.connect(host=DB_HOST, port=DB_PORT, user=DB_USER, password=DB_PASS,
                       database=DB_NAME, charset='utf8mb4', autocommit=True,
                       connect_timeout=15)
cur = conn.cursor()

print('==== SHOW TABLES ====')
cur.execute('SHOW TABLES')
rows = cur.fetchall()
print('TOTAL TABLES:', len(rows))
tables = [r[0] for r in rows]
for t in tables:
    print(t)

print()
print('==== DESCRIBE 相关表 ====')
targets = [
    't_user',
    't_video',
    't_video_publicize',
    't_drama_publicize',
    't_drama_unlock',
    't_ta_ku_callback',
    't_ta_ku_config',
    't_bill_gold',
    't_task_complete_record',
]
for t in targets:
    if t in tables:
        print(f'\n--- DESCRIBE {t} ---')
        cur.execute(f'DESCRIBE `{t}`')
        for col in cur.fetchall():
            print('  ', col[0], col[1], 'NULL' if col[2] == 'YES' else 'NOT NULL', 'PK' if col[3] == 'PRI' else '',
                  'DEFAULT=' + str(col[4]) if col[4] is not None else '', col[5])
    else:
        print(f'\n--- {t} NOT FOUND ---')

conn.close()
