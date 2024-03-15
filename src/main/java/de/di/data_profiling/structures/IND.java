package de.di.data_profiling.structures;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class IND {

    private String lhsRelation;
    private List<String> lhsAttributes;

    private String rhsRelation;
    private List<String> rhsAttributes;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("IND(")
                .append(this.lhsRelation)
                .append(this.lhsAttributes)
                .append(", ")
                .append(this.rhsRelation)
                .append(this.rhsAttributes)
                .append(")");
        return builder.toString();
    }
}
