USE health_diet;

CREATE TABLE `user_tbl` (
  `open_id` varchar(255) NOT NULL,
  `union_id` varchar(255) NOT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `birthday` varchar(45) DEFAULT NULL,
  `height` varchar(45) DEFAULT NULL,
  `weight` varchar(45) DEFAULT NULL,
  `sport_rate` varchar(45) DEFAULT NULL,
  `nephrotic_period` varchar(45) DEFAULT NULL,
  `treatment_method` varchar(255) DEFAULT NULL,
  `other_diseases` varchar(255) DEFAULT NULL,
  `irritability` varchar(255) DEFAULT NULL,
  `user_info` TEXT,
  PRIMARY KEY (`open_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `food_tbl` (
  `food_id` varchar(45) NOT NULL,
  `food_code` varchar(45) DEFAULT NULL,
  `sub_code` varchar(45) DEFAULT NULL,
  `sub_name` varchar(45) DEFAULT NULL,
  `food_name` varchar(256) DEFAULT NULL,
  `food_alias` varchar(256) DEFAULT NULL,
  `water` varchar(45) DEFAULT NULL,
  `energy` float(10,2) DEFAULT NULL,
  `protein` float(10,2) DEFAULT NULL,
  `fat` varchar(45) DEFAULT NULL,
  `cho` varchar(45) DEFAULT NULL,
  `ca` varchar(45) DEFAULT NULL,
  `p` varchar(45) DEFAULT NULL,
  `k` varchar(45) DEFAULT NULL,
  `na` varchar(45) DEFAULT NULL,
  `unit` varchar(45) DEFAULT NULL,
  `edible` int DEFAULT NULL,
  PRIMARY KEY (`food_id`),
  FULLTEXT KEY `NAME_INDEX` (`food_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `food_weight_tbl` (
  `food_id` varchar(45) NOT NULL,
  `food_code` varchar(45) DEFAULT NULL,
  `sub_code` varchar(45) DEFAULT NULL,
  `protein_weight` int DEFAULT NULL,
  `fat_weight` int DEFAULT NULL,
  `cho_weight` int DEFAULT NULL,
--   `k_weight` int DEFAULT NULL,
  `na_weight` int DEFAULT NULL,
  `cholesterol_weight` int DEFAULT NULL,
  `purine_weight` int DEFAULT NULL,
--   `p_weight` int DEFAULT NULL,
  PRIMARY KEY (`food_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `recipe_tbl` (
  `recipe_id` varchar(45) NOT NULL,
  `recipe_name` varchar(45) DEFAULT NULL,
  `cook_method` varchar(45) DEFAULT NULL,
  `taste` varchar(45) DEFAULT NULL,
  `cuisine` varchar(45) DEFAULT NULL,
  `age_group` varchar(45) DEFAULT NULL,
  `difficulty` varchar(45) DEFAULT NULL,
  `prepare_time` int DEFAULT NULL,
  `cooking_time` int DEFAULT NULL,
  `meal_time` varchar(45) DEFAULT NULL,
  `category` varchar(45) DEFAULT NULL,
  `material` varchar(45) DEFAULT NULL,
  `main_ingredients` varchar(256) DEFAULT NULL,
  `supplementary` varchar(256) DEFAULT NULL,
  `cookingnote` TEXT,
  `energy` float(10,2) DEFAULT NULL,
  `protein` float(10,2) DEFAULT NULL,
  `ckd_category` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`recipe_id`),
  FULLTEXT KEY `NAME_INDEX` (`recipe_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `recipe_weight_tbl` (
  `recipe_id` varchar(45) NOT NULL,
  `material` varchar(45) DEFAULT NULL,
  `protein_weight` int DEFAULT NULL,
  `fat_weight` int DEFAULT NULL,
  `cho_weight` int DEFAULT NULL,
  `na_weight` int DEFAULT NULL,
  `cholesterol_weight` int DEFAULT NULL,
  `purine_weight` int DEFAULT NULL,
  `k_weight` int DEFAULT NULL,
  PRIMARY KEY (`recipe_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `food_log_tbl` (
  `open_id` VARCHAR(255) NOT NULL,
  `log_date` DATETIME NOT NULL,
  `is_completed_log` TINYINT NULL DEFAULT 0,
  `totalEnergy` float(10,2) DEFAULT NULL,
  `totalProtein` float(10,2) DEFAULT NULL,
  `peRatio` float(10,2) DEFAULT NULL,
  `fat` float(10,2) DEFAULT NULL,
  `feRatio` float(10,2) DEFAULT NULL,
  `cho` float(10,2) DEFAULT NULL,
  `ceRatio` float(10,2) DEFAULT NULL,
  `na` float(10,2) DEFAULT NULL,
  `k` float(10,2) DEFAULT NULL,
  `p` float(10,2) DEFAULT NULL,
  `ca` float(10,2) DEFAULT NULL,
  PRIMARY KEY (`open_id`, `log_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `food_log_detail_tbl` (
  `open_id` VARCHAR(255) NOT NULL,
  `log_date` DATETIME NOT NULL,
  `mealtime` VARCHAR(45) NOT NULL,
  `content` TEXT NULL,
  PRIMARY KEY (`open_id`, `log_date`, `mealtime`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `food_recommended_tbl` (
  `cdk_period` VARCHAR(128) NOT NULL,
  `weight` int NOT NULL,
  `energy` float(10,2) DEFAULT NULL,
  `protein` float(10,2) DEFAULT NULL,
  `grains_b_r` float(10,2) DEFAULT NULL,
  `grains_b_p` float(10,2) DEFAULT NULL,
  `grains_l_r` float(10,2) DEFAULT NULL,
  `grains_l_p` float(10,2) DEFAULT NULL,
  `grains_d_r` float(10,2) DEFAULT NULL,
  `grains_d_p` float(10,2) DEFAULT NULL,

  `starch_b_r` float(10,2) DEFAULT NULL,
  `starch_b_p` float(10,2) DEFAULT NULL,
  `starch_l_r` float(10,2) DEFAULT NULL,
  `starch_l_p` float(10,2) DEFAULT NULL,
  `starch_d_r` float(10,2) DEFAULT NULL,
  `starch_d_p` float(10,2) DEFAULT NULL,

  `wege_b_r` float(10,2) DEFAULT NULL,
  `wege_b_p` float(10,2) DEFAULT NULL,
  `wege_l_r` float(10,2) DEFAULT NULL,
  `wege_l_p` float(10,2) DEFAULT NULL,
  `wege_d_r` float(10,2) DEFAULT NULL,
  `wege_d_p` float(10,2) DEFAULT NULL,

  `wege_fruit_b_r` float(10,2) DEFAULT NULL,
  `wege_fruit_b_p` float(10,2) DEFAULT NULL,
  `wege_fruit_l_r` float(10,2) DEFAULT NULL,
  `wege_fruit_l_p` float(10,2) DEFAULT NULL,
  `wege_fruit_d_r` float(10,2) DEFAULT NULL,
  `wege_fruit_d_p` float(10,2) DEFAULT NULL,

  `milk_b_r` float(10,2) DEFAULT NULL,
  `milk_b_p` float(10,2) DEFAULT NULL,
  `milk_l_r` float(10,2) DEFAULT NULL,
  `milk_l_p` float(10,2) DEFAULT NULL,
  `milk_d_r` float(10,2) DEFAULT NULL,
  `milk_d_p` float(10,2) DEFAULT NUll,

  `eggs_b_r` float(10,2) DEFAULT NULL,
  `eggs_b_p` float(10,2) DEFAULT NULL,
  `eggs_l_r` float(10,2) DEFAULT NULL,
  `eggs_l_p` float(10,2) DEFAULT NULL,
  `eggs_d_r` float(10,2) DEFAULT NULL,
  `eggs_d_p` float(10,2) DEFAULT NUll,


  `fat_b_r` float(10,2) DEFAULT NULL,
  `fat_b_p` float(10,2) DEFAULT NULL,
  `fat_l_r` float(10,2) DEFAULT NULL,
  `fat_l_p` float(10,2) DEFAULT NULL,
  `fat_d_r` float(10,2) DEFAULT NULL,
  `fat_d_p` float(10,2) DEFAULT NUll,

  `fruit_a_r` float(10,2) DEFAULT NULL,
  `fruit_a_p` float(10,2) DEFAULT NULL,
  PRIMARY KEY (`cdk_period`, `weight`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;