#!/bin/ruby1.8

# Summary: Statistics of Loto 6/49, 2005 - 2010
# Author:  Tibi Turbureanu (tct) 
# License: GNU GPLv3+

require 'rubygems'
require 'nokogiri'
require 'open-uri'
require 'gruff'

# official results from Romanian National Lottery
loto_urls = {
             "2010" => "http://www.loto.ro/index.php/649/rezultate.html?showall=1", 
             "2009" => "http://www.loto.ro/index.php/649/rezultate/133-rezultate-649-2009.html?showall=1", 
             "2008" => "http://www.loto.ro/index.php/649/rezultate/132-rezultate-649-2008.html?showall=1", 
             "2007" => "http://www.loto.ro/index.php/649/rezultate/131-rezultate-649-2007.html?showall=1", 
             "2006" => "http://www.loto.ro/index.php/649/rezultate/130-rezultate-649-2006.html?showall=1", 
             "2005" => "http://www.loto.ro/index.php/649/rezultate/129-rezultate-649-2005.html?showall=1",
            }

# XPath select query
number_xpath  = "//h3[@id='numbers-castig']"

# { year => [occurence_of(1), occurence_of(2), ... ] }
occurences_by_year = Hash.new


Loto 6/49

loto_urls.each do |year, url|
  # open HTML document at URL
  doc = Nokogiri::HTML(open(url))
  # get selected elements 
  elements = doc.xpath(number_xpath)

  # [occurence_of(1), occurence_of(2), ... ]
  occurences_by_number = Array.new(49) { 0 }

  elements.each do |element|
    # get number from HTML element
    number = element.content.to_i
    # increment occurence of number
    occurences_by_number[number-1] += 1
  end

  # save in big hash of occurences by year
  occurences_by_year[year] = occurences_by_number
end

# new side stacked bar graph of size 1200x900
graph = Gruff::SideStackedBar.new(1200)

graph.title = "Loto 6/49"

2010.downto(2005) do |year|
  # add graph data representing occurences
  graph.data(year, occurences_by_year[year.to_s].reverse)
end

# hack to set labels 49 down to 1
labels = Hash.new()
(0..48).each do |i|
  labels[i] = (49-i).to_s
end
graph.labels = labels

# arrange marker font size to fit graph
graph.marker_font_size = 10

# finally write PNG image (SVG is faulty)
graph.write('loto_649.png')


