package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    private final List<TimeEntry> timeEntries = new ArrayList<>();
    private int lastId = 0;

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        lastId++;
        TimeEntry createdTimeEntry = new TimeEntry(
                lastId,
                timeEntry.getProjectId(),
                timeEntry.getUserId(),
                timeEntry.getDate(),
                timeEntry.getHours()
        );
        timeEntries.add(createdTimeEntry);
        return createdTimeEntry;
    }

    @Override
    public TimeEntry find(long id) {
        return timeEntries.stream()
                .filter(timeEntry -> timeEntry.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<TimeEntry> list() {
        return timeEntries;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        int index = timeEntries.indexOf(find(id));
        if (index < 0) {
            return null;
        }
        timeEntry.setId(id);
        timeEntries.set(index, timeEntry);
        return timeEntry;
    }

    @Override
    public void delete(long id) {
        int index = timeEntries.indexOf(find(id));
        if (index < 0) {
            return;
        }
        timeEntries.remove(index);
    }
}
