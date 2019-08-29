package com.peruapps.icnclient.extensions

/**
 * Calls the specified function [block] `this` value as its argument.
 * Don´t return nothing.
 * The block will be invoked only if [T] is not null. It returns a VAL inside the block.
 */
inline fun <T> T?.safeValue(block: (T) -> Unit) {
    if (this != null)
        block(this)
}