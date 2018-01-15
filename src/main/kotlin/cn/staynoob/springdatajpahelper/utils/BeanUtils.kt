package cn.staynoob.springdatajpahelper.utils

import org.springframework.beans.BeanUtils
import org.springframework.beans.BeanWrapperImpl
import kotlin.reflect.KProperty

@Suppress("RemoveRedundantSpreadOperator")
fun <T : Any> copy(src: T, target: T) {
    copy(src, target, *arrayOf<String>())
}

/**
 * copy properties exclude ignore properties
 */
fun <T : Any> copy(src: T, target: T, vararg ignore: KProperty<*>) {
    copy(src, target, *ignore.map { it.name }.toTypedArray())
}

/**
 * copy properties exclude null properties and ignore properties
 */
fun <T : Any> merge(src: T, target: T, vararg ignore: KProperty<*>) {
    val ignoreProperties = (ignore.map { it.name }.toSet()
            + getIgnoreProperties(src)).toTypedArray()
    copy(src, target, *ignoreProperties)
}

/**
 * copy properties exclude ignore properties
 */
fun <T : Any> copy(src: T, target: T, vararg ignore: String) {
    if (src === target) return
    BeanUtils.copyProperties(src, target, *ignore)
}

/**
 * should ignore:
 * - readonly property
 * - null property
 */
internal fun getIgnoreProperties(entity: Any): Set<String> {
    val wrapper = BeanWrapperImpl(entity)
    val (writable, unWritable) = wrapper.propertyDescriptors
            .partition { it.writeMethod != null }
    return unWritable.map { it.name }.toSet() +
            writable.map { it.name }
                    .filter { wrapper.getPropertyValue(it) == null }
                    .toSet()
}

