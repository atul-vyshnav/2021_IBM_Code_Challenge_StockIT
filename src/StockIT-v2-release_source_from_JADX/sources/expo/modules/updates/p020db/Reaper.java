package expo.modules.updates.p020db;

import android.util.Log;
import expo.modules.updates.UpdatesConfiguration;
import expo.modules.updates.launcher.SelectionPolicy;
import expo.modules.updates.manifest.ManifestMetadata;
import expo.modules.updates.p020db.entity.AssetEntity;
import expo.modules.updates.p020db.entity.UpdateEntity;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* renamed from: expo.modules.updates.db.Reaper */
public class Reaper {
    private static String TAG = Reaper.class.getSimpleName();

    public static void reapUnusedUpdates(UpdatesConfiguration updatesConfiguration, UpdatesDatabase updatesDatabase, File file, UpdateEntity updateEntity, SelectionPolicy selectionPolicy) {
        if (updateEntity == null) {
            Log.d(TAG, "Tried to reap while no update was launched; aborting");
            return;
        }
        updatesDatabase.updateDao().deleteUpdates(selectionPolicy.selectUpdatesToDelete(updatesDatabase.updateDao().loadAllUpdatesForScope(updatesConfiguration.getScopeKey()), updateEntity, ManifestMetadata.getManifestFilters(updatesDatabase, updatesConfiguration)));
        List<AssetEntity> deleteUnusedAssets = updatesDatabase.assetDao().deleteUnusedAssets();
        LinkedList linkedList = new LinkedList();
        for (AssetEntity next : deleteUnusedAssets) {
            if (!next.markedForDeletion) {
                String str = TAG;
                Log.e(str, "Tried to delete asset with URL " + next.url + " but it was not marked for deletion");
            } else {
                File file2 = new File(file, next.relativePath);
                try {
                    if (file2.exists() && !file2.delete()) {
                        String str2 = TAG;
                        Log.e(str2, "Failed to delete asset with URL " + next.url + " at path " + file2.toString());
                        linkedList.add(next);
                    }
                } catch (Exception e) {
                    String str3 = TAG;
                    Log.e(str3, "Failed to delete asset with URL " + next.url + " at path " + file2.toString(), e);
                    linkedList.add(next);
                }
            }
        }
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            AssetEntity assetEntity = (AssetEntity) it.next();
            File file3 = new File(file, assetEntity.relativePath);
            try {
                if (file3.exists()) {
                    if (!file3.delete()) {
                        String str4 = TAG;
                        Log.e(str4, "Retried and failed again deleting asset with URL " + assetEntity.url + " at path " + file3.toString());
                    }
                }
                linkedList.remove(assetEntity);
            } catch (Exception e2) {
                String str5 = TAG;
                Log.e(str5, "Retried and failed again deleting asset with URL " + assetEntity.url + " at path " + file3.toString(), e2);
                linkedList.add(assetEntity);
            }
        }
    }
}
