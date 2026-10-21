# Kivy Launcher

(work in progress, not yet published on Google Play)

This is a reboot of the previously pygame/kivy launcher, implemented in Java in Python for android. It was barely maintainable, and with the rewrite of the new Python for android, it was lost.

This version aimed to provide a replacement for the launcher, but works also on desktop, on Python 2 or 3.

Anybody can clone the repo, add the dependencies we would not provide by default, and recompile it.

![kivy-launcher](https://user-images.githubusercontent.com/37904/37256979-0611d5be-2563-11e8-98a6-485e656b0f4b.png)

## How it works

Follow the guide the same as before:

https://kivy.org/docs/guide/packaging-android.html#packaging-your-application-for-the-kivy-launcher

On Android, choose the folder containing your applications the first time you
start the launcher. The folder should contain one subdirectory per application,
with an `android.txt` and `main.py` in each. The launcher remembers the
user-granted folder access and copies its contents into private storage when you
refresh the list. This uses Android's Storage Access Framework, not broad
shared-storage access.

The Android build targets API 36 (Android 16, required for new Google Play
submissions as of August 31, 2026), supports API 21 and later, and runs on
Android 14 and later. It packages both `armeabi-v7a` (32-bit ARM) and
`arm64-v8a`. It uses Kivy's SDL2 bootstrap and OpenGL ES 2.0 rendering. Python
3, Kivy, KivyMD, Pyjnius, and ffpyplayer are declared in `buildozer.spec`;
the local python-for-android recipes build FFmpeg 6.1.2 for each selected ABI,
matching the FFmpeg APIs used by ffpyplayer 4.5.1.

Build a debug APK with:

```sh
buildozer android debug
```

The pinned python-for-android revision builds Python 3.14.2. Its local
ffpyplayer recipe uses Cython 3.1.2 to generate code compatible with that
Python version. Install `requirements.txt` for desktop development and the
Buildozer host tool.

## `android.txt` specification

- `title`: Title of the application
- `author`: Author of the application
- `orientation`: Default orientation, one of "landscape", "portrait", "sensor"

## Works

- Provide a simple UI to discover and start another app
- Start another main.py as a `__name__ == '__main__'`
- Reduce to the minimum the overhead of the launcher to launch another app
- Support landscape / portrait / sensor

## Ideas

- Act as a server to just launch any Kivy-based app from desktop to mobile
- Ability to configure multiple paths to look for applications
- Different ordering: by name, last updated, size
- Add tiny icon to show what application orientation is
- Allow to change multiple configuration token / environemnt (like different density/dpi to simulate other screens)
- Support for application without "android.txt"
