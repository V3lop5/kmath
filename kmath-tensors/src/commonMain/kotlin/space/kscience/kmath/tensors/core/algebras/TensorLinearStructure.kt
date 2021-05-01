/*
 * Copyright 2018-2021 KMath contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */

package space.kscience.kmath.tensors.core.algebras

import space.kscience.kmath.nd.Strides
import kotlin.math.max


internal fun stridesFromShape(shape: IntArray): IntArray {
    val nDim = shape.size
    val res = IntArray(nDim)
    if (nDim == 0)
        return res

    var current = nDim - 1
    res[current] = 1

    while (current > 0) {
        res[current - 1] = max(1, shape[current]) * res[current]
        current--
    }
    return res

}

internal fun indexFromOffset(offset: Int, strides: IntArray, nDim: Int): IntArray {
    val res = IntArray(nDim)
    var current = offset
    var strideIndex = 0

    while (strideIndex < nDim) {
        res[strideIndex] = (current / strides[strideIndex])
        current %= strides[strideIndex]
        strideIndex++
    }
    return res
}

internal fun stepIndex(index: IntArray, shape: IntArray, nDim: Int): IntArray {
    val res = index.copyOf()
    var current = nDim - 1
    var carry = 0

    do {
        res[current]++
        if (res[current] >= shape[current]) {
            carry = 1
            res[current] = 0
        }
        current--
    } while (carry != 0 && current >= 0)

    return res
}


public class TensorLinearStructure(override val shape: IntArray) : Strides
{
    override val strides: IntArray
        get() = stridesFromShape(shape)

    override fun index(offset: Int): IntArray =
        indexFromOffset(offset, strides, shape.size)

    public fun stepIndex(index: IntArray): IntArray =
        stepIndex(index, shape, shape.size)

    override val linearSize: Int
        get() = shape.reduce(Int::times)

    public val dim: Int
        get() = shape.size

}