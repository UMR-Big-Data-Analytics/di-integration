package de.di.data_profiling.structures;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class UCC {

    private String relation;
    private Set<String> attributes;

    @Override
    public String toString() {
        List<String> attributes = new ArrayList<>(this.attributes);
        Collections.sort(attributes);
        StringBuilder builder = new StringBuilder();
        builder.append("UCC(")
                .append(this.relation)
                .append(attributes)
                .append(")");
        return builder.toString();
    }
}
