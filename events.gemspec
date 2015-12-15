# coding: utf-8
lib = File.expand_path('../lib', __FILE__)
$LOAD_PATH.unshift(lib) unless $LOAD_PATH.include?(lib)
require 'events/version'

Gem::Specification.new do |spec|
  spec.name          = "events"
  spec.version       = Events::VERSION
  spec.authors       = ["ebl"]
  spec.email         = ["eviatharbl@gmail.com"]
  spec.summary       = %q{Inject new relic to rails apps}
  spec.homepage      = ""
  spec.license       = "MIT"

  spec.files         = `git ls-files -z`.split("\x0")
  spec.executables   = spec.files.grep(%r{^bin/}) { |f| File.basename(f) }
  spec.test_files    = spec.files.grep(%r{^(test|spec|features)/})
  spec.require_paths = ["lib"]

end
