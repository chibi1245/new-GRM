import org.gradle.api.JavaVersion

@Suppress("ConstPropertyName")
object ProjectConfig {
    private const val versionMajor = 1
    private const val versionMinor = 0
    private const val versionPatch = 4

    const val packageName = "com.snsop.attendance"

    const val minSdk = 26
    const val compileSdk = 36
    const val targetSdk = 36
    val versionCode = genVersionCode(versionMajor, versionMinor, versionPatch)
    val versionName = genVersionName(versionMajor, versionMinor, versionPatch)

    const val IS_DEBUG = true

    val javaVersion = JavaVersion.VERSION_21
    const val BASE_URL_LIVE = "https://gwapp.southsudansafetynet.info"
    const val BASE_URL_DEV = "https://devgwapp.southsudansafetynet.info"

    fun genVersionCode(major: Int, minor: Int, patch: Int) = major * 10_000 + minor * 100 + patch
    fun genVersionName(major: Int, minor: Int, patch: Int) = "$major.$minor.$patch"
}