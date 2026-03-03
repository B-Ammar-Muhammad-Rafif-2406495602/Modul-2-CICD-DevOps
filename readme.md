## Reflection

1) Explain what principles you apply to your project!
SRP: 
sebelumnya CarController berada pada inner class di dalam Product Controller. hal ini bisa membuat Product Controller jadi punya dua responsibility
(untuk nge lola http request produk dan mobil) after menerapkan SRP, CarController ktia buat jadi file sendiri "CarController.java" dengan @RequestMapping("/car")
sedangkan ProductController hanyaa menangani route product. 

OCP:
Interface ProductService dan CarService sudah menerapkan implementasi yang mendukung OCP dengan benar karena ProductServiceImpl dan CarServiceImpl bisa diganti tanpa mengubah interface ataupun controller

LSP:
pada before-solid CarController extends ProductController. ini violation terhadap LSP karena CarController bukan subtype dari ProductController jadi CarController tidak bisa
menggantikan ProductController dimanapun tanpa merusak behaviour program. CarController menangani mobil sedangkan ProductController menangani product.
setelah mengimplementasikan LSP CarController tidak lagi meng-extend ProductController, saat ini dua duanya kelas @Controller dan berdiri secara mandiri

ISP:
disini CarService dan ProductService sudah dipisah dengan baik pada setelah CarController dipisah
CarController hanya bergantung pada CarService dan ProductController hanya bergantu pada ProductService. 

DIP:
awalnya CarController langsung nge inject CarServiceImpl secara langsung, artinya high-level module si CarController jadi bergantung sama modul yang lebih low level.
setelah menerapkan DIP, dependencynya sekarang di declare terhadap interface, kini Controller terpisah dari implementasi concretenya tanpa menyentuh controller sama sekali

2) Explain the advantages of applying SOLID principles to your project with examples.
Dengan menerapkan SRP, ketika domain mobil berubah, kita hanya perlu mengubah CarController. di kode lama tiap perubahan CarController kita harus membuka ProductController sehingga beresiko menubah logic product
sehingga penerapan SRP membuat kita lebih mudah memelihara dan mengubah program

dengan menerapkan DIP, CarController bergantung pada interface CarService. nantinya ketika melakukan unittest kita bsia langsung inject moc carService tanpa perlu CarServiceImpl yang asli
maupun database, hal ini membuat unit test lebih mudah nantinya

dari banyaknya solid yang diterapkan secara keseluruhan kode jadi lebih mudah dipahami, fleksibel, dan juga mudah dikembangkan


3) Explain the disadvantages of not applying SOLID principles to your project with examples.
salah satu disadvantage yang saya temui adalah adanya bug yang tersebar ke beberapa fitur yag tidak berkaitan, contoh pada CarRepository.update() update dimana car.getCarId().equals(id)
membandingkan dengan int id = 0 yang dimana harusnya parameter string, bug ini secara diam diam membuat setiap update pada mobil gagal dan mengembalikan null
jika dari awal sudah menerapkan OCP seharusnya bug ini bisa di deteksi lebih awal