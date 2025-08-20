# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# by the Android Gradle plugin.
# You can edit this file to add your own rules.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView, uncomment the following lines to ensure
# that the JavaScript interface can be accessed.
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# If you use the Gson library in your project, uncomment the following
# line to preserve the names of members reflected upon during serialization.
#-keepclassmembers class * {
#    @com.google.gson.annotations.SerializedName <fields>;
#}

# Keep all public methods in ViewModels so Data Binding can access them
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    public <methods>;
}