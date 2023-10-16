package main

import (
	"math"
	"math/rand"
	"time"
)

type Fighter struct {
	energy    int
	monastery string
}

func createEnergyChain(size int) chan Fighter {
	energy := make(chan Fighter, size)

	s := rand.NewSource(time.Now().Unix())
	random := rand.New(s)

	monasteries := []string{"Guang-Yin", "Guang-Yan"}
	print("--------------YOUR FIGHTERS--------------")
	println()
	for i := 0; i < size; i++ {
		val := random.Intn(150) + 1
		monastery := monasteries[i%2] // Розподілити ченців між монастирями
		fighter := Fighter{energy: val, monastery: monastery}
		print("Fighter from ", fighter.monastery, " with energy level: ", fighter.energy)
		println()
		energy <- fighter
	}
	println()
	return energy
}

func fight(energyRead chan Fighter, energyWrite chan Fighter) {
	rival1 := <-energyRead
	rival2 := <-energyRead
	if rival1.energy > rival2.energy {
		println("Fight", rival1.monastery, "(", rival1.energy, ")", "vs", rival2.monastery, "(", rival2.energy, ")", ":", rival1.monastery, "wins")
		energyWrite <- rival1
	} else {
		println("Fight", rival1.monastery, "(", rival1.energy, ")", "vs", rival2.monastery, "(", rival2.energy, ")", ":", rival2.monastery, "wins")
		energyWrite <- rival2
	}
}

func main() {
	const size = 16
	energy := createEnergyChain(size)

	rounds := int(math.Log2(size))
	fights := size / 2
	print("-----------------BATTLE------------------")
	println()
	for round := 1; round <= rounds; round++ {
		nextEnergy := make(chan Fighter, fights)
		if round == rounds {
			go fight(energy, nextEnergy)
			winner := <-nextEnergy
			println("\nWinner of the fight is:", winner.monastery, "(", winner.energy, ")")
			break
		}

		for i := 0; i < fights; i++ {
			go fight(energy, nextEnergy)
		}

		fights /= 2
		energy = nextEnergy
	}
}
