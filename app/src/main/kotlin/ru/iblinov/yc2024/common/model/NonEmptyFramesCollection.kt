package ru.iblinov.yc2024.common.model

/**
 * Не потокобезопасная коллекция для работы с кадрами
 */
class NonEmptyFramesCollection(
    first: FrameData,
) {

    private var firstNode = Node.createSingle(first)

    private var currentNode = firstNode

    fun getCurrentNode() = currentNode

    suspend fun asyncWithSavedNode(
        onFinally: () -> Unit,
        block: suspend () -> Unit,
    ) {
        val savedNode = currentNode
        try {
            block()
        } finally {
            safeUpdateCurrentNode(savedNode)
            onFinally()
        }
    }

    fun updateToFirst() {
        currentNode = currentNode.next ?: firstNode
    }

    fun updateToNextOrFirst() {
        currentNode = currentNode.next ?: firstNode
    }

    fun removeCurrent(createIfEmpty: () -> FrameData) {
        val previous = currentNode.previous
        val next = currentNode.next
        previous?.next = currentNode.next
        next?.previous = currentNode.previous

        when {
            previous != null -> currentNode = previous
            next != null -> currentNode = next
            else -> {
                currentNode = Node.createSingle(createIfEmpty())
                firstNode = currentNode
            }
        }
    }

    fun add(frameData: FrameData) {
        val newNode = Node(
            previous = currentNode,
            value = frameData,
            next = currentNode.next
        )
        currentNode.next?.previous = newNode
        currentNode.next = newNode
        currentNode = newNode
    }

    fun hasMoreThanOneNode(): Boolean = firstNode.next != null

    fun withIterator() = FramesIterator(firstNode)

    fun safeUpdateCurrentNode(node: Node) {
        val next = node.next
        val previous = node.previous
        require(
            (next == null || next.previous == node) &&
                    (previous == null || previous.next == node)
        ) { "Invalid node" }

        currentNode = node
    }

    class Node(
        var previous: Node?,
        val value: FrameData,
        var next: Node?
    ) {
        companion object {
            fun createSingle(
                value: FrameData,
            ): Node = Node(
                previous = null,
                value = value,
                next = null
            )
        }
    }

    class FramesIterator(
        private val firstNode: Node
    ) : Iterable<Node> {

        override fun iterator(): Iterator<Node> = object : Iterator<Node> {
            private var current = firstNode
            private var wasFirst = false

            override fun hasNext(): Boolean = !wasFirst || current.next != null

            override fun next(): Node {
                if (!wasFirst) {
                    wasFirst = true
                    return current
                }
                val next = requireNotNull(current.next)
                current = next
                return next
            }
        }

    }
}