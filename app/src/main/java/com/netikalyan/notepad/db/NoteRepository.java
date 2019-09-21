/*
 * MIT License
 *
 * Copyright (c) 2019 netikalyan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.netikalyan.notepad.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class NoteRepository {
    private final NoteDAO mNoteDAO;
    private final LiveData<List<Note>> mNotesList;
    private final LiveData<List<Note>> mArchivedNotesList;
    private final LiveData<List<Note>> mDeletedNotesList;

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getDatabase(application);
        mNoteDAO = database.noteDAO();
        mNotesList = mNoteDAO.getAllNotes();
        mArchivedNotesList = mNoteDAO.getArchivedNotes();
        mDeletedNotesList = mNoteDAO.getDeletedNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return mNotesList;
    }

    public LiveData<List<Note>> getArchivedNotes() {
        return mArchivedNotesList;
    }

    public LiveData<List<Note>> getDeletedNotes() {
        return mDeletedNotesList;
    }

    public void insert(Note note) {
        new InsertTask(mNoteDAO).execute(note);
    }

    public void delete(Note note) {
        new DeleteTask(mNoteDAO).execute(note);
    }

    public void deleteAll() {
        new DeleteAllTask(mNoteDAO).execute();
    }

    public void update(Note note) {
        new UpdateTask(mNoteDAO).execute(note);
    }

    private static class DeleteAllTask extends AsyncTask<Void, Void, Void> {
        private final NoteDAO noteDAO;

        DeleteAllTask(NoteDAO dao) {
            this.noteDAO = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            this.noteDAO.deleteAllNotes();
            return null;
        }
    }

    private static class DeleteTask extends AsyncTask<Note, Void, Void> {
        private final NoteDAO noteDAO;

        DeleteTask(NoteDAO dao) {
            this.noteDAO = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            this.noteDAO.delete(notes[0]);
            return null;
        }
    }

    private static class UpdateTask extends AsyncTask<Note, Void, Void> {
        private final NoteDAO noteDAO;

        UpdateTask(NoteDAO dao) {
            this.noteDAO = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            this.noteDAO.update(notes[0]);
            return null;
        }
    }

    private static class InsertTask extends AsyncTask<Note, Void, Void> {
        private final NoteDAO noteDAO;

        InsertTask(NoteDAO dao) {
            this.noteDAO = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            this.noteDAO.insert(notes[0]);
            return null;
        }
    }
}
