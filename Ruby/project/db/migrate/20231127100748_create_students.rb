class CreateStudents < ActiveRecord::Migration[7.0]
  def change
    create_table :students do |t|
      t.string :first_name
      t.string :middle_name
      t.string :last_name
      t.string :group
      t.integer :algebra_score
      t.integer :geometry_score
      t.integer :computer_science_score


      t.timestamps
    end
  end
end
