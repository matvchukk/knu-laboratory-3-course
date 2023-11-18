#Variant 9

def gaussian_elimination(matrix, vector)
  0.upto(matrix.length - 2) do |pivot_idx|
    max_rel_val = 0
    max_idx = pivot_idx

    (pivot_idx).upto(matrix.length - 1) do |row|
      rel_val = matrix[row][pivot_idx] / matrix[row].map(&:abs).max

      if rel_val >= max_rel_val
        max_rel_val = rel_val
        max_idx = row
      end
    end

    matrix[pivot_idx], matrix[max_idx] = matrix[max_idx], matrix[pivot_idx]
    vector[pivot_idx], vector[max_idx] = vector[max_idx], vector[pivot_idx]

    pivot = matrix[pivot_idx][pivot_idx]

    (pivot_idx + 1).upto(matrix.length - 1) do |row|
      factor = matrix[row][pivot_idx] / pivot
      matrix[row][pivot_idx] = 0.0

      (pivot_idx + 1).upto(matrix[row].length - 1) do |col|
        matrix[row][col] -= factor * matrix[pivot_idx][col]
      end

      vector[row] -= factor * vector[pivot_idx]
    end
  end

  return [matrix, vector]
end

def back_substitution(matrix, vector)
  (matrix.length - 1).downto(0) do |row|
    tail = vector[row]

    (row + 1).upto(matrix.length - 1) do |col|
      tail -= matrix[row][col] * vector[col]
      matrix[row][col] = 0.0
    end

    vector[row] = tail / matrix[row][row]
    matrix[row][row] = 1.0
  end
end

def fill_matrix(n, k)
  matrix = Array.new(n) { Array.new(n) }

  n -= 1
  for i in 0..n do
    for j in 0..n do
      matrix[i][j] = if i == j
                       2.0
                     else
                       k + 2.0
                     end
    end
  end

  return matrix
end

def fill_vector(n)
  vec = Array.new(n)

  n -= 1
  for i in 0..n do
    vec[i] = i + 1.0
  end

  return vec
end

print "Enter number from the interval [3..9]: n = "
$n = gets.chomp
print "\n"

while $n.to_f < 3 || $n.to_f > 9 do
  print "Enter number FROM THE INTERVAL [3..9]: n = "
  $n = gets.chomp
  print "\n"
end

n = $n.to_f
k = 9 
A = fill_matrix(n, k)
b = fill_vector(n)

puts "Initial matrix A:"
A.each { |row| puts row.inspect }
puts "\nInitial vector b:"
puts b.inspect

gaussian_elimination(A, b)
back_substitution(A, b)

puts "\nAfter elimination A:"
A.each { |row| puts row.inspect }
puts "\nVector of result:"
puts b.inspect
