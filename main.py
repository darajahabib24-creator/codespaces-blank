from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.button import Button
from kivy.uix.label import Label

class MainApp(App):
    def build(self):
        # Main vertical layout
        layout = BoxLayout(orientation='vertical', padding=20, spacing=10)
        
        # UI Elements
        self.label = Label(text="Welcome to your Kivy App!", font_size='20sp')
        btn = Button(text="Click Me", size_hint=(1, 0.3), background_color=(0, 0.6, 0.8, 1))
        btn.bind(on_press=self.on_button_click)
        
        # Assemble UI
        layout.add_widget(self.label)
        layout.add_widget(btn)
        
        return layout

    def on_button_click(self, instance):
        self.label.text = "Hello from Android!"

if __name__ == '__main__':
    MainApp().run()
