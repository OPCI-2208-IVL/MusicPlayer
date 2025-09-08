plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    // 移除: alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization) // 使用版本目录中的定义
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 31
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17 // 改为 17
        targetCompatibility = JavaVersion.VERSION_17 // 改为 17
    }
    kotlinOptions {
        jvmTarget = "17" // 改为 17
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13" // 添加 Compose 编译器版本
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.androidx.navigation.compose)
//  implementation(libs.androidx.tools.core)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.media3.session)
    implementation(libs.androidx.media3.exoplayer)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.androidx.compose.material.iconsExtended)

    debugImplementation(libs.chucker)
    releaseImplementation(libs.chucker.no.op)

    //kotlin序列化
    //https://kotlinlang.org/docs/serialization.html
    implementation(libs.kotlinx.serialization.json)

    //region 网络框架
    //https://github.com/square/okhttp
    implementation(libs.okhttp)

    //网络框架日志框架
    implementation(libs.okhttp3.logging.interceptor)

    //类型安全网络框架
    //https://github.com/square/retrofit
    implementation(libs.retrofit2.retrofit)

    //让Retrofit支持Kotlinx Serialization
    implementation(libs.jakewharton.retrofit2.kotlinx.serialization.converter)
    //endregion

    //图片加载框架
    //https://github.com/coil-kt/coil
    implementation(libs.coil.compose)

    //region 依赖注入
    //https://developer.android.google.cn/training/dependency-injection/hilt-android?hl=zh-cn
    ksp(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    kspAndroidTest(libs.hilt.android.compiler)
    androidTestImplementation(libs.hilt.android.testing)
    //endregion

    implementation(libs.org.jetbrains.kotlinx.kotlinx.coroutines.guava)

    implementation(libs.androidx.constraintlayout.compose)

    compileOnly(libs.ksp.gradlePlugin)
}