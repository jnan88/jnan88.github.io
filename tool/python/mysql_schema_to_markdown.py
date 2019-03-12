# -*- coding: utf-8 -*-
# Created by qizai on 2019/3/12
# 根据mysql数据生成markdown数据字典
import pymysql
#
DB_NAME='db2'
conn = pymysql.connect(host='127.0.0.1',user='root',password='TV',database='information_schema')
##########################
cursor = conn.cursor()
cursor.execute("select table_name,TABLE_COMMENT from information_schema.tables where table_schema='{}' and table_type='base table'".format(DB_NAME))
tables = cursor.fetchall()

markdown_table_header = """

### %s - %s
字段名 | 字段类型 | 默认值 | 注解
---- | ---- | ---- | ---- 
"""
markdown_table_row = """%s | %s | %s | %s
"""
#保存输出结果
f = open('markdown.md','w')
for table in tables:
    sql = "select COLUMN_NAME,COLUMN_TYPE,COLUMN_DEFAULT,COLUMN_COMMENT from information_schema.COLUMNS where table_schema='{}' and table_name='{}'".format(DB_NAME,table[0])
    #print(sql)
    cursor.execute(sql)
    tmp_table = cursor.fetchall()
    p = markdown_table_header % table;
    for col in tmp_table:
        defval='NULL'
        if col[2]:
            defval=col[2]
        p += markdown_table_row % (col[0],col[1],defval,col[3])
    f.writelines(p)
f.close()
