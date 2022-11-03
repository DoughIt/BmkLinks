#!/usr/bin/env bash
# 替换 jar 包路径为本地实际地址
echo "alias bookmark='Bookmark(){ java -jar ./BmkLinks.jar $1;};Bookmark' # 运行命令行书签管理器" >> ~/.bashrc
source ~/.bashrc