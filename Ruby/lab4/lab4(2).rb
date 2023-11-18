#Variant 9

def generate_matrix_and_vector(n)
  matrix_a = Array.new(n) { Array.new(n) { |i| i == (n - 1) ? 1 : rand(10) } }
  vector_x = Array.new(n) { rand(10) }
  return matrix_a, vector_x
end

def calculate_matrix_multiplication(vector_input, matrix_input)
  if vector_input.length != matrix_input[0].length
    return "The number of columns of the first matrix must be equal to the number of rows of the second!"
  end

  vector_output = Array.new(matrix_input.length, 0)

  matrix_input.each_with_index do |row, i|
    row.each_with_index do |element, j|
      vector_output[i] += element * vector_input[j]
    end
  end

  return vector_output
end

n = 8
a, x = generate_matrix_and_vector(n)

puts "Generated matrix A:"
a.each { |row| puts row.inspect }

puts "\nGenerated vector X:"
puts x.inspect

result = calculate_matrix_multiplication(x, a)
puts "\nThe product of the column vector X by the matrix A:"
puts result.inspect
