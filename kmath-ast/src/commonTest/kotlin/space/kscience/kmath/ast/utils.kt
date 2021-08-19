/*
 * Copyright 2018-2021 KMath contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
 */

package space.kscience.kmath.ast

import space.kscience.kmath.expressions.*
import space.kscience.kmath.operations.DoubleField
import space.kscience.kmath.operations.IntRing

internal interface CompilerTestContext {
    fun MST.compileToExpression(algebra: IntRing): Expression<Int>
    fun MST.compile(algebra: IntRing, arguments: Map<Symbol, Int>): Int
    fun MST.compile(algebra: IntRing, vararg arguments: Pair<Symbol, Int>): Int = compile(algebra, mapOf(*arguments))
    fun MST.compileToExpression(algebra: DoubleField): Expression<Double>
    fun MST.compile(algebra: DoubleField, arguments: Map<Symbol, Double>): Double

    fun MST.compile(algebra: DoubleField, vararg arguments: Pair<Symbol, Double>): Double =
        compile(algebra, mapOf(*arguments))
}

internal object InterpreterTestContext : CompilerTestContext{
    override fun MST.compileToExpression(algebra: IntRing): Expression<Int> = toExpression(algebra)
    override fun MST.compile(algebra: IntRing, arguments: Map<Symbol, Int>): Int = interpret(algebra,arguments)
    override fun MST.compileToExpression(algebra: DoubleField): Expression<Double> = toExpression(algebra)

    override fun MST.compile(algebra: DoubleField, arguments: Map<Symbol, Double>): Double =
        interpret(algebra, arguments)
}

internal expect inline fun runCompilerTest(action: CompilerTestContext.() -> Unit)
