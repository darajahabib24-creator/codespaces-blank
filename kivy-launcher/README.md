# Kivy Launcher

(work in progress, not yet published on Google Play)

This is a reboot of the previously pygame/kivy launcher, implemented in Java in Python for android. It was barely maintainable, and with the rewrite of the new Python for android, it was lost.

This version aimed to provide a replacement for the launcher, but works also on desktop, on Python 2 or 3.

Anybody can clone the repo, add the dependencies we would not provide by default, and recompile it.

![kivy-launcher](https://user-images.githubusercontent.com/37904/37256979-0611d5be-2563-11e8-98a6-485e656b0f4b.png)

## How it works

Follow the guide the same as before:

https://kivy.org/docs/guide/packaging-android.html#packaging-your-application-for-the-kivy-launcher

Then just start the launcher, you should see your application listed, then press play.

On Android, put projects in their own subfolders under `/storage/emulated/0/kivy`
or the shared `Download` folder. Each project folder must contain an
`android.txt` file. The launcher searches both `download` and `Download`
spellings. Do not place projects under `/data/user/0/`: Android restricts that
private app directory to the owning app. Reading projects from shared storage
on Android 11 and later requires granting the launcher **all-files access**
in system settings. Paths in `KIVYLAUNCHER_PATHS` are also searched.
The build targets Android API 34 and 32-bit ARM (`armeabi-v7a`) with the SDL2
bootstrap; Kivy supplies the OpenGL ES rendering support.

Build a debug APK with Buildozer:

```sh
buildozer android debug
```

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
