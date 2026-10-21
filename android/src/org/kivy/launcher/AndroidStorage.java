package org.kivy.launcher;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class AndroidStorage {
    private AndroidStorage() {
    }

    public static void copyTree(Context context, String treeUriString,
                                String destinationPath) throws IOException {
        Uri treeUri = Uri.parse(treeUriString);
        File destination = new File(destinationPath);
        File parent = destination.getParentFile();
        if (parent == null || (!parent.isDirectory() && !parent.mkdirs())) {
            throw new IOException("Cannot create apps folder parent");
        }

        File temporary = new File(parent, destination.getName() + ".tmp");
        File previous = new File(parent, destination.getName() + ".previous");
        deleteRecursively(temporary);
        deleteRecursively(previous);
        if (!temporary.mkdirs()) {
            throw new IOException("Cannot create temporary apps folder");
        }

        try {
            copyChildren(
                context,
                treeUri,
                DocumentsContract.getTreeDocumentId(treeUri),
                temporary
            );
        } catch (IOException error) {
            deleteRecursively(temporary);
            throw error;
        }

        boolean hadDestination = destination.exists();
        if (hadDestination && !destination.renameTo(previous)) {
            deleteRecursively(temporary);
            throw new IOException("Cannot preserve the existing apps folder");
        }
        if (!temporary.renameTo(destination)) {
            if (hadDestination && !previous.renameTo(destination)) {
                throw new IOException(
                    "Cannot install the refreshed apps folder or restore the previous copy"
                );
            }
            deleteRecursively(temporary);
            throw new IOException("Cannot install the refreshed apps folder");
        }
        deleteRecursively(previous);
    }

    private static void copyChildren(Context context, Uri treeUri,
                                     String parentDocumentId, File directory)
            throws IOException {
        ContentResolver resolver = context.getContentResolver();
        Uri childrenUri = DocumentsContract.buildChildDocumentsUriUsingTree(
            treeUri, parentDocumentId
        );
        String[] projection = {
            DocumentsContract.Document.COLUMN_DOCUMENT_ID,
            DocumentsContract.Document.COLUMN_DISPLAY_NAME,
            DocumentsContract.Document.COLUMN_MIME_TYPE
        };

        try (Cursor cursor = resolver.query(childrenUri, projection, null, null, null)) {
            if (cursor == null) {
                throw new IOException("Cannot list the selected apps folder");
            }
            while (cursor.moveToNext()) {
                String documentId = cursor.getString(0);
                String name = safeName(cursor.getString(1));
                String mimeType = cursor.getString(2);
                File child = new File(directory, name);

                if (DocumentsContract.Document.MIME_TYPE_DIR.equals(mimeType)) {
                    if (!child.mkdirs() && !child.isDirectory()) {
                        throw new IOException("Cannot create folder: " + name);
                    }
                    copyChildren(context, treeUri, documentId, child);
                } else {
                    copyFile(
                        resolver,
                        DocumentsContract.buildDocumentUriUsingTree(treeUri, documentId),
                        child
                    );
                }
            }
        } catch (SecurityException error) {
            throw new IOException("Access to the selected apps folder was lost", error);
        }
    }

    private static void copyFile(ContentResolver resolver, Uri uri, File destination)
            throws IOException {
        try (InputStream input = resolver.openInputStream(uri);
             OutputStream output = new FileOutputStream(destination)) {
            if (input == null) {
                throw new IOException("Cannot read selected file: " + uri);
            }
            byte[] buffer = new byte[8192];
            int count;
            while ((count = input.read(buffer)) != -1) {
                output.write(buffer, 0, count);
            }
        }
    }

    private static String safeName(String name) throws IOException {
        if (name == null || name.isEmpty() || ".".equals(name) || "..".equals(name)
                || name.contains("/") || name.contains("\\")) {
            throw new IOException("The selected folder contains an invalid filename");
        }
        return name;
    }

    private static void deleteRecursively(File file) throws IOException {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children == null) {
                throw new IOException("Cannot clean temporary apps folder");
            }
            for (File child : children) {
                deleteRecursively(child);
            }
        }
        if (!file.delete()) {
            throw new IOException("Cannot clean temporary apps folder");
        }
    }
}
