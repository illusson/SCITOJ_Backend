package io.github.sgpublic.aidescit.api.core.util

import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.Predicate

fun Predicate?.and(builder: CriteriaBuilder, next: Predicate): Predicate {
    this ?: return next
    return builder.and(this, next)
}

fun Predicate?.or(builder: CriteriaBuilder, next: Predicate): Predicate {
    this ?: return next
    return builder.or(this, next)
}

fun <T> HashSet<T>?.retainAll(next: HashSet<T>): HashSet<T> {
    this ?: return next
    retainAll(next)
    return this
}

fun <T> HashSet<T>?.removeAll(next: HashSet<T>): HashSet<T> {
    this ?: return next
    removeAll(next)
    return this
}