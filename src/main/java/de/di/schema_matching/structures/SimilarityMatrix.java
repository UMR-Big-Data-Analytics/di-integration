package de.di.schema_matching.structures;

import de.di.Relation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class SimilarityMatrix {

    private final double[][] matrix;

    private final Relation sourceRelation;

    private final Relation targetRelation;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("SimilarityMatrix(")
                .append(this.sourceRelation.getName()).append(", ").append(this.targetRelation.getName()).append('\n');
        for (int i = 0; i < this.matrix.length; i++) {
            builder.append("[");
            for (int j = 0; j < this.matrix[0].length; j++) {
                builder.append(String.format("%1.3f", this.matrix[i][j]));
                if (j < this.matrix[0].length - 1)
                    builder.append(", ");
            }
            builder.append("]");
            if (i < this.matrix.length - 1)
                builder.append('\n');
            else
                builder.append(")");
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        SimilarityMatrix that = (SimilarityMatrix) o;
        return Arrays.deepEquals(this.getMatrix(), that.getMatrix()) &&
                Objects.equals(this.getSourceRelation(), that.getSourceRelation()) &&
                Objects.equals(this.getTargetRelation(), that.getTargetRelation());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(this.getSourceRelation(), this.getTargetRelation());
        result = 31 * result + Arrays.deepHashCode(this.getMatrix());
        return result;
    }
}
