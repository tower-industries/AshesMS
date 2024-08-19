package net;

import client.processor.npc.FredrickProcessor;
import note.NoteService;

import java.util.Objects;

public record ChannelDependencies(NoteService noteService, FredrickProcessor fredrickProcessor) {

    public ChannelDependencies {
        Objects.requireNonNull(noteService);
        Objects.requireNonNull(fredrickProcessor);
    }
}
