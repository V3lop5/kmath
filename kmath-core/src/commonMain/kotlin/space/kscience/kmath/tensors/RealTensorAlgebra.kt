package space.kscience.kmath.tensors

import space.kscience.kmath.structures.RealBuffer
import space.kscience.kmath.structures.array
import kotlin.math.abs
import kotlin.math.max


public class RealTensorAlgebra : TensorPartialDivisionAlgebra<Double, RealTensor> {

    override fun RealTensor.value(): Double {
        check(this.shape contentEquals intArrayOf(1)) {
            "Inconsistent value for tensor of shape ${shape.toList()}"
        }
        return this.buffer.array[0]
    }

    override fun eye(n: Int): RealTensor {
        val shape = intArrayOf(n, n)
        val buffer = DoubleArray(n * n) { 0.0 }
        val res = RealTensor(shape, buffer)
        for (i in 0 until n) {
            res[intArrayOf(i, i)] = 1.0
        }
        return res
    }

    override fun zeros(shape: IntArray): RealTensor {
        TODO("Not yet implemented")
    }

    override fun zeroesLike(other: RealTensor): RealTensor {
        // TODO refactor very bad!!!
        val shape = other.shape
        val buffer = DoubleArray(other.buffer.size) { 0.0 }
        return RealTensor(shape, buffer)
    }

    override fun ones(shape: IntArray): RealTensor {
        TODO("Not yet implemented")
    }

    override fun onesLike(shape: IntArray): RealTensor {
        TODO("Not yet implemented")
    }

    override fun RealTensor.copy(): RealTensor {
        // should be rework as soon as copy() method for NDBuffer will be available
        return RealTensor(this.shape, this.buffer.array.copyOf())
    }


    override fun Double.plus(other: RealTensor): RealTensor {
        val resBuffer = DoubleArray(other.buffer.size) { i ->
            other.buffer.array[i] + this
        }
        return RealTensor(other.shape, resBuffer)
    }

    override fun RealTensor.plus(value: Double): RealTensor = value + this

    override fun RealTensor.plus(other: RealTensor): RealTensor {
        val broadcast = broadcastTensors(this, other)
        val newThis = broadcast[0]
        val newOther = broadcast[1]
        val resBuffer = DoubleArray(newThis.buffer.size) { i ->
            newThis.buffer.array[i] + newOther.buffer.array[i]
        }
        return RealTensor(newThis.shape, resBuffer)
    }

    override fun RealTensor.plusAssign(value: Double) {
        for (i in this.buffer.array.indices) {
            this.buffer.array[i] += value
        }
    }

    override fun RealTensor.plusAssign(other: RealTensor) {
        //todo should be change with broadcasting
        for (i in this.buffer.array.indices) {
            this.buffer.array[i] += other.buffer.array[i]
        }
    }

    override fun Double.minus(other: RealTensor): RealTensor {
        val resBuffer = DoubleArray(other.buffer.size) { i ->
            this - other.buffer.array[i]
        }
        return RealTensor(other.shape, resBuffer)
    }

    override fun RealTensor.minus(value: Double): RealTensor {
        val resBuffer = DoubleArray(this.buffer.size) { i ->
            this.buffer.array[i] - value
        }
        return RealTensor(this.shape, resBuffer)
    }

    override fun RealTensor.minus(other: RealTensor): RealTensor {
        val broadcast = broadcastTensors(this, other)
        val newThis = broadcast[0]
        val newOther = broadcast[1]
        val resBuffer = DoubleArray(newThis.buffer.size) { i ->
            newThis.buffer.array[i] - newOther.buffer.array[i]
        }
        return RealTensor(newThis.shape, resBuffer)
    }

    override fun RealTensor.minusAssign(value: Double) {
        for (i in this.buffer.array.indices) {
            this.buffer.array[i] -= value
        }
    }

    override fun RealTensor.minusAssign(other: RealTensor) {
        TODO("Alya")
    }

    override fun Double.times(other: RealTensor): RealTensor {
        //todo should be change with broadcasting
        val resBuffer = DoubleArray(other.buffer.size) { i ->
            other.buffer.array[i] * this
        }
        return RealTensor(other.shape, resBuffer)
    }

    //todo should be change with broadcasting
    override fun RealTensor.times(value: Double): RealTensor = value * this

    override fun RealTensor.times(other: RealTensor): RealTensor {
        //todo should be change with broadcasting
        val resBuffer = DoubleArray(this.buffer.size) { i ->
            this.buffer.array[i] * other.buffer.array[i]
        }
        return RealTensor(this.shape, resBuffer)
    }

    override fun RealTensor.timesAssign(value: Double) {
        //todo should be change with broadcasting
        for (i in this.buffer.array.indices) {
            this.buffer.array[i] *= value
        }
    }

    override fun RealTensor.timesAssign(other: RealTensor) {
        //todo should be change with broadcasting
        for (i in this.buffer.array.indices) {
            this.buffer.array[i] *= other.buffer.array[i]
        }
    }

    override fun RealTensor.unaryMinus(): RealTensor {
        val resBuffer = DoubleArray(this.buffer.size) { i ->
            this.buffer.array[i].unaryMinus()
        }
        return RealTensor(this.shape, resBuffer)
    }

    override fun RealTensor.dot(other: RealTensor): RealTensor {
        TODO("Alya")
    }

    override fun diagonalEmbedding(diagonalEntries: RealTensor, offset: Int, dim1: Int, dim2: Int): RealTensor {
        TODO("Alya")
    }

    override fun RealTensor.transpose(i: Int, j: Int): RealTensor {
        checkTranspose(this.dimension, i, j)
        val n = this.buffer.size
        val resBuffer = DoubleArray(n)

        val resShape = this.shape.copyOf()
        resShape[i] = resShape[j].also { resShape[j] = resShape[i] }

        val resTensor = RealTensor(resShape, resBuffer)

        for (offset in 0 until n) {
            val oldMultiIndex = this.strides.index(offset)
            val newMultiIndex = oldMultiIndex.copyOf()
            newMultiIndex[i] = newMultiIndex[j].also { newMultiIndex[j] = newMultiIndex[i] }

            val linearIndex = resTensor.strides.offset(newMultiIndex)
            resTensor.buffer.array[linearIndex] = this.buffer.array[offset]
        }
        return resTensor
    }


    override fun RealTensor.view(shape: IntArray): RealTensor {
        return RealTensor(shape, this.buffer.array)
    }

    override fun RealTensor.viewAs(other: RealTensor): RealTensor {
        return this.view(other.shape)
    }

    override fun RealTensor.abs(): RealTensor {
        TODO("Not yet implemented")
    }



    override fun RealTensor.sum(): RealTensor {
        TODO("Not yet implemented")
    }


    override fun RealTensor.div(value: Double): RealTensor {
        TODO("Not yet implemented")
    }

    override fun RealTensor.div(other: RealTensor): RealTensor {
        TODO("Not yet implemented")
    }

    override fun RealTensor.divAssign(value: Double) {
        TODO("Not yet implemented")
    }

    override fun RealTensor.divAssign(other: RealTensor) {
        TODO("Not yet implemented")
    }

    override fun RealTensor.exp(): RealTensor {
        TODO("Not yet implemented")
    }

    override fun RealTensor.log(): RealTensor {
        TODO("Not yet implemented")
    }


    override fun RealTensor.lu(): Pair<RealTensor, IntTensor> {
        // todo checks
        val lu = this.copy()
        val m = this.shape[0]
        val pivot = IntArray(m)


        // Initialize permutation array and parity
        for (row in 0 until m) pivot[row] = row
        var even = true

        for (i in 0 until m) {
            var maxA = -1.0
            var iMax = i

            for (k in i until m) {
                val absA = abs(lu[k, i])
                if (absA > maxA) {
                    maxA = absA
                    iMax = k
                }
            }

            //todo check singularity

            if (iMax != i) {

                val j = pivot[i]
                pivot[i] = pivot[iMax]
                pivot[iMax] = j
                even != even

                for (k in 0 until m) {
                    val tmp = lu[i, k]
                    lu[i, k] = lu[iMax, k]
                    lu[iMax, k] = tmp
                }

            }

            for (j in i + 1 until m) {
                lu[j, i] /= lu[i, i]
                for (k in i + 1 until m) {
                    lu[j, k] -= lu[j, i] * lu[i, k]
                }
            }
        }
        return Pair(lu, IntTensor(intArrayOf(m), pivot))
    }

    override fun luUnpack(lu: RealTensor, pivots: IntTensor): Triple<RealTensor, RealTensor, RealTensor> {
        // todo checks
        val n = lu.shape[0]
        val p = zeroesLike(lu)
        pivots.buffer.array.forEachIndexed { i, pivot ->
            p[i, pivot] = 1.0
        }
        val l = zeroesLike(lu)
        val u = zeroesLike(lu)

        for (i in 0 until n){
            for (j in 0 until n){
                if (i == j) {
                    l[i, j] = 1.0
                }
                if (j < i) {
                    l[i, j] = lu[i, j]
                }
                if (j >= i) {
                    u[i, j] = lu[i, j]
                }
            }
        }
        return Triple(p, l, u)
    }

    override fun RealTensor.svd(): Triple<RealTensor, RealTensor, RealTensor> {
        /**
         * Main first task for @AlyaNovikova
         */
        TODO("Not yet implemented")
    }

    override fun RealTensor.symEig(eigenvectors: Boolean): Pair<RealTensor, RealTensor> {
        TODO("Not yet implemented")
    }

}

public inline fun <R> RealTensorAlgebra(block: RealTensorAlgebra.() -> R): R =
    RealTensorAlgebra().block()