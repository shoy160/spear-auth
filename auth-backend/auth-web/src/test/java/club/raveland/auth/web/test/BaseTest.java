package club.raveland.auth.web.test;

import club.raveland.core.session.RavelandSession;
import club.raveland.core.session.SessionDTO;

import javax.annotation.Resource;
import java.io.Closeable;

/**
 * @author shay
 * @date 2021/3/9
 */
public abstract class BaseTest {

    @Resource
    protected RavelandSession session;

    private final SessionDTO currentSession;

    protected BaseTest() {
        currentSession = new SessionDTO("148c08585fc2124f", "148c083380021192");
    }

    protected void useSession(Runnable action) {
        try (Closeable ignored = session.use(currentSession)) {
            action.run();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
