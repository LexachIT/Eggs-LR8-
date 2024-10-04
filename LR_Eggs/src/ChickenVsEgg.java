import java.util.concurrent.atomic.AtomicReference;
public class ChickenVsEgg {
    private static class CustomThread extends Thread {
        private final String message;
        private final AtomicReference<String> lastMessage;
        public CustomThread(String message, AtomicReference<String> lastMessage) {
            this.message = message;
            this.lastMessage = lastMessage;
        }
        @Override
        public void run() {
            for (int i = 0; i < 50; i++) {
                try {
                    // Задержка с использованием случайного времени
                    Thread.sleep((int) (Math.random() * 100));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                lastMessage.set(message); // Обновляем последнее сообщение
                System.out.println(message); // Выводим сообщение
            }
        }
    }
    public static void main(String[] args) {
        AtomicReference<String> lastMessage = new AtomicReference<>();
        CustomThread chickenThread = new CustomThread("Курица", lastMessage);
        CustomThread eggThread = new CustomThread("Яйцо", lastMessage);
        chickenThread.start();
        eggThread.start();
        try {
            chickenThread.join();
            eggThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        // Выводим последнее сообщение
        System.out.println("Последнее слово потока: " + lastMessage.get());
    }
}