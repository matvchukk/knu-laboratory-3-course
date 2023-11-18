#Variant 9

def determine_figure_area(figure_coordinates)
  n = figure_coordinates.length
  area = 0.0

  (0..n-1).each do |k|
    xk, yk = figure_coordinates[k]
    xk1, yk1 = figure_coordinates[(k + 1) % n]
    area += (xk + xk1) * (yk - yk1)
  end

  area = (area.abs) / 2.0
  return area
end

figure_coordinates = [
  [52,289], [46,258], [63,240], [94,234], [132,236],
  [184,234], [198,207], [171,181], [120,171], [92,157],
  [49,134], [43,99], [63,82], [109,69], [157,59],
  [202,57], [245,51], [278,47], [309,41], [340,31],
  [379,33], [415,61], [430,86], [447,140], [474,196],
  [483,245], [471,274], [411,292], [350,302], [299,308],
  [250,309], [193,308], [154,308], [111,309], [84,302]
]

area = determine_figure_area(figure_coordinates)
puts "Area of figure: #{area}"
puts

figure1_coordinates = [
  [52, 289], [46, 258], [63, 240], [94, 234], [132, 236]
]

figure2_coordinates = [
  [184, 234], [198, 207], [171, 181], [120, 171], [92, 157]
]

figure3_coordinates = [
  [49, 134], [43, 99], [63, 82], [109, 69], [157, 59]
]

figure4_coordinates = [
  [202, 57], [245, 51], [278, 47], [309, 41], [340, 31]
]

figure5_coordinates = [
  [379, 33], [415, 61], [430, 86], [447, 140], [474, 196]
]

figure6_coordinates = [
  [483, 245], [471, 274], [411, 292], [350, 302], [299, 308]
]

figure7_coordinates = [
  [250, 309], [193, 308], [154, 308], [111, 309], [84, 302]
]

[figure1_coordinates, figure2_coordinates, figure3_coordinates, figure4_coordinates, figure5_coordinates, figure6_coordinates, figure7_coordinates].each_with_index do |coordinates, index|
  area = determine_figure_area(coordinates)
  puts "Area of figure #{index + 1}: #{area}"
end
