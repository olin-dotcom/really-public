class Note < ActiveRecord::Base
  validates :description, :presence => true
  default_scope order("#{table_name}.created_at desc")
end
