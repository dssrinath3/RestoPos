package hiaccounts.in.restopos.utility;

import hiaccounts.in.restopos.application.model.ConfigSplash;

public class ValidationUtil {
    public static int hasPath(ConfigSplash cs) {
        if (cs.getPathSplash().isEmpty())
            return Flags.WITH_LOGO;
        else
            return Flags.WITH_PATH;
    }
}
