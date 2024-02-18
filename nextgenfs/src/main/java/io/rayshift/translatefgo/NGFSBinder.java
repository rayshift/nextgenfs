/*package io.rayshift.translatefgo;

import android.app.Service;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.system.Os;
import android.util.Log;

import rikka.shizuku.Shizuku;
import rikka.shizuku.ShizukuProvider;
import rikka.shizuku.shared.BuildConfig;


public class NGFSBinder {
    private INGFSService binder = null;

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder myBinder) {
            if (myBinder != null && myBinder.pingBinder()) {
                binder = INGFSService.Stub.asInterface(myBinder);
                Log.i("TranslateFGO", "Bound NGFS.");

                try {
                    int out = binder.add(4, 4);
                }
                catch (RemoteException e) {
                    e.printStackTrace();
                    Log.e("TranslateFGO", String.format("Error: %s", Log.getStackTraceString(e)));
                }
            }
            else {
                Log.e("TranslateFGO", "Bad binder interface.");
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            binder = null;
        }
    };
    public static String GetServiceClassName() {
        return NGFSService.class.getName();
    }

    public static String GetPackageName() {
        return "io.rayshift.translatefgo";
    }

    private static final Shizuku.UserServiceArgs ServiceArgs = new Shizuku.UserServiceArgs(
                    new ComponentName(GetPackageName(), GetServiceClassName()))
            .processNameSuffix("user_service")
            .debuggable(BuildConfig.DEBUG)
            .version(22006);

    public void Bind() {
        ShizukuProvider.enableMultiProcessSupport(false);
        Log.i("TranslateFGO", "Binder pid=" + Os.getpid() + ", uid=" + Os.getuid());
        Shizuku.bindUserService(ServiceArgs, connection);
    }

    public boolean IsBound() {
        return binder == null;
    }

    public int Add(int a, int b) {
        try {
            if (binder != null) {
                return binder.add(a, b);
            }
            else {
                return -1;
            }
        }
        catch (RemoteException e) {
            e.printStackTrace();
            Log.e("TranslateFGO", String.format("Error: %s", Log.getStackTraceString(e)));
            return -1;
        }
    }
}*/
