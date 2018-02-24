[TOC]

# git使用规范
## branch分支规范
### 主分支master
代码库有且仅有一个主分支。所有提供给用户使用的正式版本，都在这个主分支上发布。
![image_1b0t4dj9q1t851t9g13msh9h6o2a.png-15.4kB][1]

### 测试分支test
当一个功能开发完成后从dev或feature分支合并到test分支，测试人员仅在test上进行测试。

### 开发分支dev
主分支只用来发布重大版本，日常开发应该在另一条分支上完成。我们把开发用的分支，叫做dev。
![image_1b0t4cvk81ffn1cko10ci42b1r2e1t.png-26kB][2]

将dev分支发布到test分支的命令：
```
#分支push到远程, 远程没有的话会新建一个分支, 下次只需要git push即可
git push origin dev
# 切换到test分支
git checkout test
# 对dev分支进行合并
git merge --no-ff dev
```
这里稍微解释一下，上一条命令的--no-ff参数是什么意思。默认情况下，Git执行"快进式合并"（fast-farward merge），会直接将test分支指向dev分支。
![image_1b0t4cjr911j1trn1o2816bhm5f1g.png-42.5kB][3]
使用--no-ff参数后，会执行正常合并，在test分支上生成一个新节点。
![image_1b0t4ba41117hj9m5e71mlv113s13.png-27.2kB][4]

### 三、临时性分支
除了常设分支以外，还有一些临时性分支，用于应对一些特定目的的版本开发。临时性分支主要有三种：

* 功能（feature）分支
* 预发布（release）分支
* 修补bug（hotfix）分支

这三种分支都属于临时性需要，使用完以后，应该删除。

### 四、 功能分支

接下来，一个个来看这三种"临时性分支"。

第一种是功能分支，它是为了开发某种特定功能，从dev分支上面分出来的。开发完成后，要再并入dev。
![image_1b0t4a0oa18iu15i1n971u3pna3m.png-25.3kB][5]
功能分支的名字，可以采用feature-*的形式命名。
```
# 创建一个功能分支：
git checkout -b feature-x dev
# 开发完成后，将功能分支合并到dev分支：
git checkout dev
git merge --no-ff feature-x
# 删除feature分支：
git branch -d feature-x
```
　　
### 五、预发布分支(即demo环境)

第二种是预发布分支，它是指发布正式版本之前（即合并到master分支之前），我们可能需要有一个预发布的版本进行测试。

预发布分支是从test分支上面分出来的，预发布结束以后，必须合并进dev和master分支。它的命名，可以采用release-*的形式。
```
# 创建一个预发布分支：
git checkout -b release-1.2 dev
# 确认没有问题后，合并到master分支：
git checkout master
git merge --no-ff release-1.2
# 对合并生成的新节点，做一个标签
git tag -a 1.2
# 再合并到dev分支：
git checkout dev
git merge --no-ff release-1.2
# 最后，删除预发布分支：
git branch -d release-1.2
```

### 六、修补bug分支

最后一种是修补bug分支。软件正式发布以后，难免会出现bug。这时就需要创建一个分支，进行bug修补。

修补bug分支是从master分支上面分出来的。修补结束以后，再合并进master和dev分支。它的命名，可以采用hotfix-*的形式。
![image_1b0t48njk1fuf40l8hpjnbd339.png-23.3kB][6]
```
# 创建一个修补bug分支：
git checkout -b hotfix-0.1 master
# 修补结束后，合并到master分支：
git checkout master
git merge --no-ff hotfix-0.1
git tag -a 0.1.1
# 再合并到dev分支：
git checkout dev
git merge --no-ff hotfix-0.1
# 最后，删除"修补bug分支"：
git branch -d hotfix-0.1
```
## commit 格式
每次提交，Commit message 都包括三个部分：Header，Body 和 Footer。
```
<type>(<scope>): <subject>// 空一行<body>// 空一行<footer>
```
包括三个字段：type（必需）、scope（可选）和subject（必需）。 不管是哪一个部分，任何一行都不得超过72个字符（或100个字符）。这是为了避免自动换行影响美观。

### type

type用于说明 commit 的类别，只允许使用下面7个标识。 
 1. feat：新功能（feature） 
 2. fix：修补bug
 3. docs：文档（documentation） 
 4. style： 格式（不影响代码运行的变动）
 5. refactor：重构（即不是新增功能，也不是修改bug的代码变动）
 6. test：增加测试 
 7. chore：构建过程或辅助工具的变动

如果type为feat和fix，则该 commit 将肯定出现在 Change log 之中。

### scope

scope用于说明 commit 影响的范围，比如数据层、控制层、视图层等等，视项目不同而不同。

### subject
subject是 commit 目的的简短描述，不超过50个字符。
## 常用命令
当前git工作内容临时保存
```
git branch 查看分支
git branch <name> 创建分支
git checkout <name> 切换分支
git checkout -b <name> 创建+切换分支
git merge <name>  合并某分支到当前分支
git branch -d <name> 删除分支
git pull <远程主机名> <远程分支名>:<本地分支名>
git push <远程主机名> <本地分支名>:<远程分支名>
git clone <版本库的网址> <本地目录名>
git branch //查看当前分支
git branch -r //列出远程分支
git branch -a //列出所有分支
git branch branchName //创建分支
git checkout branchName //切换分支
git checkout -b branchName //创建并切换到分支
git checkout  //后面不跟任何参数，则就是对工作区进行检查
git checkout filename //从暂存区中恢复文件
git tag -a tagName
git tag -d tagName
git push origin tagName
git status //查看状态
# 保存当前变更
git stash
# 取出变更
git stash pop
# 删除错误commit的文件，--cached表示本地保留
git rm -r src/content/cache/* --cached
# git 修改最后一次的commit内容,或追加内容到最后一次commit
git commit --amend
# 忽略的文件
git update-index --assume-unchanged filePath
```

忽略文件
```
vim ~/.gitignoreglobal
*.pyc
*.DS_Store
git config --global core.excludesfile ~/.gitignoreglobal
```


  [1]: http://static.zybuluo.com/jnan77/r477e3oautlt39hnkir9zacb/image_1b0t4dj9q1t851t9g13msh9h6o2a.png
  [2]: http://static.zybuluo.com/jnan77/91uj7zconcrvz97f2j2q278f/image_1b0t4cvk81ffn1cko10ci42b1r2e1t.png
  [3]: http://static.zybuluo.com/jnan77/mu3nd410414kese41tuq4hp0/image_1b0t4cjr911j1trn1o2816bhm5f1g.png
  [4]: http://static.zybuluo.com/jnan77/0siolbbpof177u9wo0361wun/image_1b0t4ba41117hj9m5e71mlv113s13.png
  [5]: http://static.zybuluo.com/jnan77/gglbiao7oqqi60t8iudj2dvc/image_1b0t4a0oa18iu15i1n971u3pna3m.png
  [6]: http://static.zybuluo.com/jnan77/ptis3jyz9kxzabvhurxp91tv/image_1b0t48njk1fuf40l8hpjnbd339.png
