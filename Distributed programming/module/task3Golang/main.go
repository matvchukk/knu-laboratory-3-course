package main

import (
	"container/list"
	"sync"
	"time"
)

var operatorSync sync.WaitGroup

func customerRoutine(operator int, clientsQueue *list.List, lock *sync.Mutex) {
	for {
		lock.Lock()
		if clientsQueue.Len() > 0 {
			num := clientsQueue.Front().Value.(int)
			println("Operator #", operator+1, " is helping Client #", num)
			front := clientsQueue.Front()
			clientsQueue.Remove(front)
		}
		lock.Unlock()

		time.Sleep(2000 * time.Millisecond)
	}
}

func operatorRoutine(customer int, clientsQueue *list.List, lock *sync.Mutex, operatorCh chan int) {
	for {
		lock.Lock()
		println("New client (#", customer, ") joined the queue")
		clientsQueue.PushBack(customer)
		lock.Unlock()

		time.Sleep(2000 * time.Millisecond)
		lock.Lock()
		found := findInQueue(clientsQueue, customer)
		if found {
			println("Client #", customer, " left the queue")

			removeFromQueue(clientsQueue, customer)
			lock.Unlock()

			time.Sleep(500 * time.Millisecond)

		} else {
			lock.Unlock()
			operatorSync.Done()
			break
		}
	}
}

func findInQueue(queue *list.List, element int) bool {
	for currentElement := queue.Front(); currentElement != nil; currentElement = currentElement.Next() {
		if currentElement.Value.(int) == element {
			return true
		}
	}
	return false
}

func removeFromQueue(queue *list.List, element int) {
	for currentElement := queue.Front(); currentElement != nil; currentElement = currentElement.Next() {
		if currentElement.Value.(int) == element {
			queue.Remove(currentElement)
			break
		}
	}
}

func main() {
	var clientsQueue = list.New()
	var lock sync.Mutex

	operatorCh := make(chan int, 2)

	for i := 1; i <= 10; i++ {
		operatorSync.Add(1)
		go operatorRoutine(i, clientsQueue, &lock, operatorCh)
	}

	for i := 0; i < 2; i++ {
		operatorCh <- i
		go customerRoutine(i, clientsQueue, &lock)
	}

	operatorSync.Wait()
}
