
# CNN图片识别的Android客户端 

## 启动后端API

1. 已将服务端 (Tensorflow、ResNet50预训练模型) 打包为一个 [Docker镜像](https://hub.docker.com/r/yunterry/keras-vue)，可以直接运行一个Docker容器来启动后端API：

    ```sh
    docker run -p 80:80 yunterry/keras-vue
    ```

1. 然后将代码中 Retrofit 的 `baseUrl` 修改为你的容器运行地址即可

## Android App界面：

<div align=center><img src="image/1.jpg"></div>

## 本项目用到的开源库

  * [Retrofit](https://github.com/square/retrofit)
  * [Picasso](https://github.com/square/picasso)
  * [baseAdapter](https://github.com/hongyangAndroid/baseAdapter)
  * [MultiImageSelector](https://github.com/lovetuzitong/MultiImageSelector)

## Vue客户端

https://github.com/yunTerry/Keras-Vue
