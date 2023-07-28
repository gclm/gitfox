<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# Gitfox Changelog

## [Unreleased]

### Added

### Changed

### Deprecated

### Removed

### Fixed

### Security

## [1.0.1] - 2023-07-28

### Deprecated
- 升级Gradle IntelliJ Plugin到1.15.0
- 删除Gradle qodana Plugin插件

## 1.0.0 - 2023-04-06

### Changed
- 更新配置文件地址

### Deprecated
- 升级Gradle IntelliJ Plugin到1.13.3
- 升级Gradle Changelog Plugin到2.0.0
- 去除gear4j依赖替换为hutool
- 删除lombok依赖

## 0.5.0 - 2022-12-01

### Fixed
- fix: 新增支持2022.3
- fix: 删除过期方法

### Changed
- build: jdk11 -> jdk17
- build: gradle升级到7.6
- build: intellij升级到1.10.0
- build: chaos升级到gear4j
- build：lombok升级到1.18.24

## 0.4.1 - 2022-07-28

### Changed
- docs: 更新readme文档
- docs: 调整协议为MPL-2
- docs: 优化angular规范中文翻译

### Deprecated
- build: 新增支持2022.2

### Fixed
- refactor：重构插件图标
- refactor：更新默认的插件配置

## 0.4.0 - 2022-07-28

### Added
- 新增开源协议生成

### Removed
- 删除多余的GithubAction

## 0.3.1 - 2022-07-01

### Fixed
- 修复修改Item上面的下拉框不同步修改的bug
- 修复中文写入配置文件偶尔失败的问题
- 修复传递过程中的深浅拷贝问题
- 修复插件中英文混杂问题
- 修复Item重置不生效的问题

### Changed
- view层所有swing页面命名为xxPanel
- 调整数据传输原则为最小数据量
- 插件重新发布在idea仓库
- 移除无效配置

## 0.3.0 - 2022-06-30

### Added
- Commit页面新增跳过CI选项

### Fixed
- 修复设置页面选择项不存在卡死的问题
- 修复设置页面判断是否修改的逻辑不生效
- style: 修复Github 徽章显示问题

### Changed
- GitServer --> Item
- 是否显示分支选项调整到Commit页面
- 选择中英文切换页面调整为下拉框选择
- 重构ItemTab逻辑
- 更新效果图和README.md文件

## 0.2.1 - 2022-06-30

### Fixed
- ci: 修复github action

### Changed
- docs: 更新版本日志
- docs: 新增自定规范的格式和插件效果图
- [ImgBot] Optimize images
- style: 基于Idea插件格式化代码

### Deprecated
- build: 升级gradle插件版本

### Removed
- build: 删除kotlin配置

## 0.2.0 - 2022-06-30

### Added
- 💥: git 类型全部功能完成

### Changed
- ⚡️: 优化数据存储方式

## 0.1.0 - 2022-06-29

### Added
- ✨: 完成原插件gcp所有功能

## 0.0.1 - 2022-06-29

### Added
- Initial scaffold created
  from [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template)

### Removed
- Template cleanup
