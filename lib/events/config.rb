require 'socket'
require 'yaml'
module Events::Config

   def self.event_model_dir
    "#{File.expand_path(File.dirname(__FILE__))}/schema"
  end

end
