package data_types;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;

public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {

    @Override
    public LocalTime unmarshal(String v) throws Exception {
        return v != null ? LocalTime.parse(v) : null;
    }

    @Override
    public String marshal(LocalTime v) throws Exception {
        return v != null ? v.toString() : null;
    }
}
