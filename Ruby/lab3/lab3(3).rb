#Variant 9

#1) 1 -(2/3)x + (3/4)x**2 - (4/5)x**3 + ...+ (11/12)x**10
def first_task(x)
    sum = 1
    for i in 1..10 do
        sum += (-1)**i * x * (i+1)/(i+2)
    end
    return sum
end

#2) 1 + 1/3 + 1/3**2 + ...+ 1/3**8
def second_task()
    sum = 1
    for i in 1..8 do 
        sum += 1.0/(3**i)
    end
    return sum
end

x = 2
puts "1 -(2/3)x + (3/4)x**2 - (4/5)x**3 + ...+ (11/12)x**10 = ", first_task(x), "\n"
puts "1 + 1/3 + 1/3**2 + ...+ 1/3**8 = ", second_task()