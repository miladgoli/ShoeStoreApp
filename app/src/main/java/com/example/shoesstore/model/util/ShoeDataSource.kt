package com.example.shoesstore.model.util

import com.example.shoesstore.model.entity.Shoe

object ShoeDataSource {

    fun getDefaultShoesList(): List<Shoe> {
        return listOf(
            Shoe(
                id = 1,
                name = "Air Max 270",
                brand = "Nike",
                price = 1500000L,
                imageUrl = """https://manilaqeshm.ir/wp-content/uploads/2022/12/djdbnj.jpg""",
                description = "نایک ایر مکس ۲۷۰ با بهره‌گیری از اولین واحد Max Air که به طور اختصاصی برای استایل روزمره طراحی شده، راحتی و نرمی بی‌نظیری را در تمام طول روز برای شما به ارمغان می‌آورد."
            ),
            Shoe(
                id = 2,
                name = "Ultraboost 1.0",
                brand = "Adidas",
                price = 1800000L,
                imageUrl = """https://adidastehran.com/wp-content/uploads/2023/09/Adidas-Ultraboost-1.0-HQ4199-4.jpg""",
                description = "با اولترابوست، انرژی بی‌پایان را تجربه کنید. این کفش که برای ارائه تجربه‌ای بی‌نهایت از دویدن طراحی شده، هر گام شما را با قدرت و نرمی پاسخ می‌دهد."
            ),
            Shoe(
                id = 3,
                name = "Classic Leather",
                brand = "Reebok",
                price = 7500000L,
                imageUrl = """https://tehranreebok.com/wp-content/uploads/2023/06/%DA%A9%D9%81%D8%B4-GY0955.jpg""",
                description = "یک طراحی کلاسیک و همیشگی. مدل کلاسیک لدر با رویه‌ای از چرم نرم، حسی مانند دستکش را برای پا فراهم کرده و با ظاهر ساده و مینیمال خود، با هر استایلی هماهنگ می‌شود."
            ),
            Shoe(
                id = 4,
                name = "Suede Classic XXI",
                brand = "Puma",
                price = 700000L,
                imageUrl = """https://cdn.sneakers123.com/release/482072/puma-suede-classic-triple-381514-01.jpg""",
                description = "پوما سوئد یک آیکون واقعی و یک کلاسیک ماندگار است که برای دهه‌ها بخش جدایی‌ناپذیر استایل خیابانی بوده و هرگز از مد نمی‌افتد."
            ),
            Shoe(
                id = 5,
                name = "574 Core",
                brand = "New Balance",
                price = 8500000L,
                imageUrl = """https://www.jakjekstore.com/wp-content/uploads/2023/12/6-72-scaled.jpg""",
                description = "مدل ۵۷۴ به عنوان یک کفش قابل اعتماد برای انجام کارهای مختلف روزمره ساخته شده و ترکیبی بی‌نظیر از دوام، راحتی و استایل چندمنظوره را ارائه می‌دهد."
            ),
            Shoe(
                id = 6,
                name = "Old Skool",
                brand = "Vans",
                price = 650000L,
                imageUrl = """https://bizweb.dktcdn.net/thumb/1024x1024/100/140/774/products/giay-vans-old-skool-oversized-laces-black-vn0007ntyp0-1-1ccae536-6a9b-4b57-8f25-76af32eeeaf2.jpg?v=1726300372030""",
                description = "اولد اسکول، کفش اسکیت کلاسیک و اولین مدلی که نوار نمادین Sidestripe ونس را به نمایش گذاشت، یک انتخاب ایده‌آل برای استایل روزمره و اسکیت‌برد است."
            ),
            Shoe(
                id = 7,
                name = "Chuck 70",
                brand = "Converse",
                price = 850000L,
                imageUrl = """https://www.converse.gr/media/catalog/product/a/0/a00916c_a_107x1_3.jpg""",
                description = "مدل چاک ۷۰ با الهام از طراحی اصلی دهه ۱۹۷۰، با استفاده از مواد اولیه باکیفیت‌تر و بالشتک‌گذاری بیشتر، به اوج راحتی و دوام رسیده است."
            ),
            Shoe(
                id = 8,
                name = "GEL-KAYANO 30",
                brand = "ASICS",
                price = 1600000L,
                imageUrl = """https://www.365rider.com/60188/zapatillas-asics-gel-kayano-30-negro-azul-aw23.jpg""",
                description = "کفش رانینگ GEL-KAYANO® ۳۰، با ترکیب حداکثر پایداری و راحتی بی‌نظیر، تجربه‌ای همراه با آرامش کامل را برای دوندگان فراهم می‌کند."
            ),
            Shoe(
                id = 9,
                name = "Air Force 1 '07",
                brand = "Nike",
                price = 1100000L,
                imageUrl = """https://images.vegnonveg.com/resized/1020X1200/10442/air-force-1-low-retro-black-black-656591ac1eec0.jpg?format=webp""",
                description = "درخشش این اسطوره بسکتبال همچنان ادامه دارد. نایک ایر فورس ۱ با طراحی کلاسیک و جزئیات مدرن، استایل شما را در هر موقعیتی متمایز می‌کند."
            ),
            Shoe(
                id = 10,
                name = "Stan Smith",
                brand = "Adidas",
                price = 1000000L,
                imageUrl = """https://assets.adidas.com/images/w_1880,f_auto,q_auto/db6794325813405b9743a8903ea54c69_9366/M20327_01_standard.jpg""",
                description = "جذابیت همیشگی، استایل بی‌دردسر و کاربرد روزمره. برای بیش از ۵۰ سال، آدیداس استن اسمیت جایگاه خود را به عنوان یک آیکون جهانی حفظ کرده است."
            ),
            Shoe(
                id = 11,
                name = "XT-6",
                brand = "Salomon",
                price = 1900000L,
                imageUrl = """https://cdn.media.amplience.net/i/office/4909409837_sd1.jpg""",
                description = "مدل XT-6 نمادی از میراث سالومون در دویدن‌های کوهستانی است؛ کفشی که انتخاب اول ورزشکاران حرفه‌ای برای مسافت‌های طولانی و مسیرهای سخت بوده است."
            ),
            Shoe(
                id = 12,
                name = "Clifton 9",
                brand = "Hoka",
                price = 1450000L,
                imageUrl = """https://cdnfa.com/mmmo/7507/files/10308682.jpg""",
                description = "نهمین نسل از سری کلیفتون، برنده جوایز متعدد، با وزنی سبک‌تر و بالشتک‌گذاری بهتر از همیشه، تجربه‌ای بی‌نظیر از دویدن را ارائه می‌دهد."
            ),
            Shoe(
                id = 13,
                name = "Cloud 5",
                brand = "On",
                price = 1399999L,
                imageUrl = """https://images.snowleader.com/cdn-cgi/image/f=auto,fit=scale-down,q=85/https://images.snowleader.com/media/catalog/product/cache/1/image/0dc2d03fe217f8c83829496872af24a0/O/N/ON__00709_01_202412180606.jpg""",
                description = "مدل محبوب Cloud 5 با مهندسی مجدد، ۴۴٪ مواد بازیافتی، فیت بهتر و راحتی بیشتر از همیشه، بازگشته است."
            )
        )
    }
}