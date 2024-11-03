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

    suspend fun withSavedNode(
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

    /**
     * @return true в случае успешного обновления до следующего элемента
     */
    fun updateToNext(): Boolean {
        val next = currentNode.next ?: return false
        currentNode = next
        return true
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

    private fun safeUpdateCurrentNode(node: Node) {
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
}