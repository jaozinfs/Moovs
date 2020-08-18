# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.kts.kts.kts.kts.kts.kts.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Coroutines
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepnames class kotlinx.coroutines.android.AndroidExceptionPreHandler {}
-keepnames class kotlinx.coroutines.android.AndroidDispatcherFactory {}

-keepclassmembernames class kotlinx.* {
    volatile <fields>;
}

-dontwarn kotlinx.atomicfu.AtomicBoolean

# Retrofit2
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keepclassmembernames interface * {
    @retrofit2.http.* <methods>;
}

# GSON Annotations
-keepclassmembers,allowobfuscation class * {
  @com.google.gson.annotations.SerializedName <fields>;
}

# DataBinding
-dontwarn androidx.databinding.**
-keep class androidx.databinding.* { *; }
-keep class * extends androidx.databinding.DataBinderMapper

# NavArgs
-keepnames class com.jaozinfs.moovs.movies.ui.fragments.MovieDetailFragmentArgs
-keepnames class com.jaozinfs.moovs.movies.ui.fragments.MovieCinemaDetailsArgs
-keepnames class com.jaozinfs.moovs.movies.ui.fragments.MoviesCinemaFragmentDirections
-keepnames class com.jaozinfs.moovs.movies.ui.fragments.MoviesFavoritesFragmentArgs
-keepnames class com.jaozinfs.moovs.movies.ui.fragments.MoviesFavoritesFragmentDirections
-keepnames class com.jaozinfs.moovs.movies.ui.fragments.MoviesFragmentDirections
#
-keepnames class com.jaozinfs.moovs.tvs.ui.fragments.FragmentTvDetailsArgs
-keepnames class com.jaozinfs.moovs.tvs.ui.fragments.FragmentTvDetailsDirections
-keepnames class com.jaozinfs.moovs.tvs.ui.fragments.FragmentTvEpisodeDetailsArgs
-keepnames class com.jaozinfs.moovs.tvs.ui.fragments.FragmentTvsCategoryArgs
-keepnames class com.jaozinfs.moovs.tvs.ui.fragments.FragmentTvsCategoryDirections
-keepnames class com.jaozinfs.moovs.tvs.ui.fragments.FragmentTvsDirections
-keepnames class com.jaozinfs.moovs.tvs.ui.fragments.FragmentTvSeasonDetailsArgs
-keepnames class com.jaozinfs.moovs.tvs.ui.fragments.FragmentTvSeasonDetailsDirections
