# My Kivy App

A simple Android app built with [Kivy](https://kivy.org) — a welcome screen with a button that says hello.

## Build the APK

The APK is built automatically by GitHub Actions on every push to `main`
(see [`.github/workflows/build.yml`](.github/workflows/build.yml)).

To download the latest APK:

1. Open the repo's **Actions** tab and click the latest **Build Android APK** run.
2. Download the **Kivy-Android-Debug-APK** artifact and install it on your phone.

## Run locally (desktop)

```bash
pip install kivy
python main.py
```

## Build locally (Android)

```bash
pip install buildozer
buildozer android debug
```

The generated APK is written to `bin/`.
