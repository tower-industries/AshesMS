package net.packet.out;

import net.opcodes.SendOpcode;
import net.packet.ByteBufOutPacket;
import note.Note;

import java.util.List;
import java.util.Objects;

import static net.PacketCreator.getTime;

public final class ShowNotesPacket extends ByteBufOutPacket {

    public ShowNotesPacket(List<Note> notes) {
        super(SendOpcode.MEMO_RESULT);
        Objects.requireNonNull(notes);

        writeByte(3);
        writeByte(notes.size());
        notes.forEach(this::writeNote);
    }

    private void writeNote(Note note) {
        writeInt(note.id());
        writeString(note.from() + " "); //Stupid nexon forgot space lol
        writeString(note.message());
        writeLong(getTime(note.timestamp()));
        writeByte(note.fame());
    }
}
