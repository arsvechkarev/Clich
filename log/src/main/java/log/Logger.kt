package log

object Logger {
  
  fun activate() {
    MyTimber.plant(MyTimber.DebugTree())
  }
  
  fun debug(lazyMessage: () -> String) {
    MyTimber.d(lazyMessage.invoke())
  }
  
  fun error(lazyMessage: () -> String, throwable: Throwable) {
    MyTimber.d(throwable, lazyMessage.invoke())
  }
  
}