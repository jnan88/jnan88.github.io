// 引入 gulp
var gulp = require('gulp'); 

// 引入组件
var jshint = require('gulp-jshint');
var sass = require('gulp-sass');
var uglify = require('gulp-uglify');
var rename = require('gulp-rename');
var concat = require('gulp-concat');//- 多个文件合并为一个；
var minifyCss = require('gulp-minify-css');//- 压缩CSS为一行；
var rev = require('gulp-rev');//- 对文件名加MD5后缀
var revCollector = require('gulp-rev-collector');//- 路径替换

// 检查脚本
gulp.task('lint', function() {
    gulp.src('./src/**/*.js')
        .pipe(jshint())
        .pipe(jshint.reporter('default'));
});

// 编译Sass
gulp.task('sass', function() {
    gulp.src('./scss/*.scss')
        .pipe(sass())
        .pipe(gulp.dest('./css'));
});

// 合并，压缩文件
gulp.task('scripts', function() {
    gulp.src('./src/**/*.js')
        .pipe(concat('all.js'))
        .pipe(gulp.dest('./dist'))
        .pipe(rename('all.min.js'))
        .pipe(uglify())
        .pipe(gulp.dest('./dist'));
});
// CSS压缩
gulp.task('minify-css', function () {
    gulp.src('css/*.css') // 要压缩的css文件
    .pipe(minifyCss()) //压缩css
    .pipe(gulp.dest('dist/css'));
});

gulp.task('concat', function() {//- 创建一个名为 concat 的 task
  gulp.src(['./css/wap_v3.1.css', './css/wap_v3.1.3.css'])//- 需要处理的css文件，放到一个字符串数组里
    .pipe(concat('wap.min.css'))//- 合并后的文件名
    .pipe(minifyCss())//- 压缩处理成一行
    .pipe(rev())//- 文件名加MD5后缀
    .pipe(gulp.dest('./css'))//- 输出文件本地
    .pipe(rev.manifest())//- 生成一个rev-manifest.json
    .pipe(gulp.dest('./rev'));//- 将 rev-manifest.json 保存到 rev 目录内
});
// 默认任务
gulp.task('default', function(){
    gulp.run('lint', 'sass', 'scripts');

    // 监听文件变化
    gulp.watch('./src/**/*.js', function(){
        gulp.run('lint', 'sass', 'scripts');
    });
});
