package de.di.schema_matching.structures;

import de.di.Relation;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@AllArgsConstructor
public class CorrespondenceMatrix {

    private final int[][] matrix;

    private final Relation sourceRelation;

    private final Relation targetRelation;

    public CorrespondenceMatrix(Relation matrixRelation, Relation source, Relation target) {
        this.matrix = new int[matrixRelation.getRecords().length][];
        for (int i = 0; i < matrixRelation.getRecords().length; i++) {
            this.matrix[i] = new int[matrixRelation.getRecords()[i].length];
            for (int j = 0; j < matrixRelation.getRecords()[i].length; j++)
                this.matrix[i][j] = Integer.parseInt(matrixRelation.getRecords()[i][j]);
        }
        this.sourceRelation = source;
        this.targetRelation = target;
    }

    @Override
    public String toString() {
        return "CorrespondenceMatrix(" + this.sourceRelation.getName() + ", " + this.targetRelation.getName() + '\n' +
                Arrays.deepToString(this.matrix).replace("], ", "]\n").replace("[[", "[").replace("]]", "]") + ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        CorrespondenceMatrix that = (CorrespondenceMatrix) o;
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
