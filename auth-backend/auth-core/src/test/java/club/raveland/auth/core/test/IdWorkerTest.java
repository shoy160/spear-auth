package club.raveland.auth.core.test;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import club.raveland.core.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author shay
 * @date 2021/3/8
 */
@Slf4j
public class IdWorkerTest {
//    private final IdWorker idWorker;
//
//    public IdWorkerTest() {
//        this.idWorker = new IdWorker(1, 1);
//    }

    @Test
    public void test() throws InterruptedException {
        ThreadFactory threadFactory = ThreadFactoryBuilder.create().setNamePrefix("id-worker-").build();
        ExecutorService exec = new ThreadPoolExecutor(5, 10,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(10), threadFactory);

        for (int j = 0; j < 10; j++) {
            IdWorker worker = new IdWorker(j + 1, 1);
            exec.submit(() -> {
                for (int i = 0; i < 100; i++) {
                    log.info(worker.nextStringId());
                }
            });
        }
        exec.awaitTermination(10, TimeUnit.SECONDS);
    }
}
